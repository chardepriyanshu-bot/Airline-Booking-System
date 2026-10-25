package com.pc.implementation;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pc.client.INotificationClient;
import com.pc.client.IUserClient;
import com.pc.enums.PaymentStatus;
import com.pc.exception.PaymentNotFoundException;
import com.pc.mapper.PaymentMapper;
import com.pc.model.Payment;
import com.pc.payload.request.EmailNotificationRequest;
import com.pc.payload.request.PaymentRequest;
import com.pc.payload.request.PaymentVerificationRequest;
import com.pc.payload.response.PaymentResponse;
import com.pc.payload.response.RazorpayOrderResponse;
import com.pc.repository.IPaymentRepository;
import com.pc.service.IPaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import lombok.RequiredArgsConstructor;
import com.pc.event.PaymentSuccessEvent;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService {

	private final IPaymentRepository paymentRepository;
	private final RazorpayClient razorpayClient;
	@Value("${razorpay.key.id}")
	private String razorpayKeyId;
	private final PaymentEventProducer paymentEventProducer;
	@Value("${razorpay.key.secret}")
	private String razorpayKeySecret;

	private final IUserClient userClient;
	private final INotificationClient notificationClient;

	@Override
	@Transactional
	public RazorpayOrderResponse initiatePayment(PaymentRequest request) {

		BigDecimal amount = request.getAmount().setScale(2);

		if (amount.signum() <= 0)
			throw new IllegalArgumentException("Payment amount must be greater than zero");

		try {

			long amountInPaise = request.getAmount().multiply(new BigDecimal("100")).longValueExact();

			JSONObject orderRequest = new JSONObject();

			orderRequest.put("amount", amountInPaise);
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "booking_" + request.getBookingId() + "_" + System.currentTimeMillis());

			Order order = razorpayClient.orders.create(orderRequest);

			Payment payment = Payment.builder().bookingId(request.getBookingId()).userId(request.getUserId())
					.amount(request.getAmount()).paymentMethod(request.getPaymentMethod())
					.paymentStatus(PaymentStatus.INITIATED).razorpayOrderId(order.get("id")).build();

			Payment savedPayment = paymentRepository.save(payment);

			return RazorpayOrderResponse.builder().paymentId(savedPayment.getPaymentId())
					.razorpayOrderId(order.get("id")).razorpayKeyId(razorpayKeyId).amount(amount).currency("INR")
					.paymentStatus(savedPayment.getPaymentStatus().name()).build();

		} catch (RazorpayException e) {
			throw new IllegalStateException("Unable to create Razorpay order", e);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public PaymentResponse getPaymentById(Long paymentId) {

		Payment payment = paymentRepository.findById(paymentId)
				.orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentId));

		return PaymentMapper.mapToResponse(payment);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PaymentResponse> getPaymentsByBookingId(Long bookingId) {

		return paymentRepository.findByBookingId(bookingId).stream().map(PaymentMapper::mapToResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<PaymentResponse> getPaymentsByUserId(Long userId) {

		return paymentRepository.findByUserId(userId).stream().map(PaymentMapper::mapToResponse).toList();
	}

	@Override
	@Transactional
	public PaymentResponse verifyPayment(PaymentVerificationRequest request) {

		Payment payment = paymentRepository.findByRazorpayOrderId(request.getRazorpayOrderId())
				.orElseThrow(() -> new PaymentNotFoundException("Payment not found"));

		String orderId = payment.getRazorpayOrderId();

		if (!orderId.equals(request.getRazorpayOrderId())) {
			throw new IllegalArgumentException("Razor order id mismatch");
		}

		if (payment.getPaymentStatus().equals(PaymentStatus.SUCCESS)) {
			return PaymentMapper.mapToResponse(payment);
		}

		try {
			JSONObject options = new JSONObject();
			options.put("razorpay_order_id", orderId);
			options.put("razorpay_payment_id", request.getRazorpayPaymentId());
			options.put("razorpay_signature", request.getRazorpaySignature());

			boolean validSignature = Utils.verifyPaymentSignature(options, razorpayKeySecret);

			if (!validSignature)
				throw new IllegalArgumentException("Invalid Razorpay payment signature");

			// Verify actual gateway payment status as well
			com.razorpay.Payment gatewayPayment = razorpayClient.payments.fetch(request.getRazorpayPaymentId());

			Object statusObject = gatewayPayment.get("status");
			String gatewayStatus = statusObject == null ? "" : statusObject.toString();

			if (!"captured".equalsIgnoreCase(gatewayStatus))
				throw new IllegalStateException("Payment is not capture yet. Gateway status: " + gatewayStatus);

			Object gatewayOrderId = gatewayPayment.get("order_id");

			if (gatewayOrderId == null || !orderId.equals(gatewayOrderId.toString())) {
				throw new IllegalArgumentException("Gateway payment does not match the saved order");
			}

			payment.setTransactionId(request.getRazorpayPaymentId());
			payment.setPaymentStatus(PaymentStatus.SUCCESS);

			Payment savedPayment = paymentRepository.save(payment);

			var user = userClient.getUserById(savedPayment.getUserId());

			PaymentSuccessEvent event = new PaymentSuccessEvent(
			        savedPayment.getPaymentId(),
			        user.getEmail(),
			        user.getFullName(),
			        savedPayment.getBookingId(),
			        savedPayment.getAmount(),
			        savedPayment.getPaymentStatus().name(),
			        LocalDateTime.now()
			       
			);

			paymentEventProducer.publicPaymentSuccess(event);

			return PaymentMapper.mapToResponse(savedPayment);

		} catch (RazorpayException ex) {
			throw new IllegalStateException("Unable to verify Razorpay payment", ex);
		}
	}

	private void sendPaymentSuccessEmail(Payment payment) {

		try {
			var user = userClient.getUserById(payment.getUserId());

			EmailNotificationRequest request = EmailNotificationRequest.builder().to(user.getEmail())
					.subject("Payment Successful - Airline Booking System")
					.message("Hello " + user.getFullName() + ",\n\n" + "Your payment was successful!\n\n"
							+ "Payment ID: " + payment.getPaymentId() + "\n" + "Booking ID: " + payment.getBookingId()
							+ "\n" + "Transaction ID: " + payment.getTransactionId() + "\n" + "Amount: INR "
							+ payment.getAmount() + "\n" + "Status: SUCCESS\n\n" + "Thank you for booking with us.")
					.build();

			notificationClient.sendEmail(request);

		} catch (Exception e) {
			System.err.println("Payment success email failed: " + e.getMessage());
		}
	}

	private void sendPaymentFailureEmail(Payment payment) {
		try {
			var user = userClient.getUserById(payment.getUserId());

			EmailNotificationRequest request = EmailNotificationRequest.builder().to(user.getEmail())
					.subject("Payment Failed - Airline Booking System")
					.message("Hello " + user.getFullName() + ",\n\n"
							+ "Unfortunately, your payment could not be completed.\n\n" + "Payment ID: "
							+ payment.getPaymentId() + "\n" + "Booking ID: " + payment.getBookingId() + "\n"
							+ "Amount: INR " + payment.getAmount() + "\n" + "Status: FAILED\n\n"
							+ "Please check your payment status before trying again.")
					.build();

			notificationClient.sendEmail(request);

		} catch (Exception e) {
			System.err.println("Payment failure email failed: " + e.getMessage());
		}
	}

}
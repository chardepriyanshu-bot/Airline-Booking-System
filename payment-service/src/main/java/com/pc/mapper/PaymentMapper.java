package com.pc.mapper;

import com.pc.model.Payment;
import com.pc.payload.request.PaymentRequest;
import com.pc.payload.response.PaymentResponse;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaymentMapper {

	public static Payment mapToEntity(PaymentRequest request) {
		
		return Payment.builder()
				.bookingId(request.getBookingId())
				.userId(request.getUserId())
				.amount(request.getAmount())
				.paymentMethod(request.getPaymentMethod())
				.build();
	}
	
	public static PaymentResponse mapToResponse(Payment payment) {
		 return PaymentResponse.builder()
	                .paymentId(payment.getPaymentId())
	                .bookingId(payment.getBookingId())
	                .userId(payment.getUserId())
	                .amount(payment.getAmount())
	                .paymentMethod(payment.getPaymentMethod())
	                .paymentStatus(payment.getPaymentStatus())
	                .transactionId(payment.getTransactionId())
	                .failureReason(payment.getFailureReason())
	                .createdAt(payment.getCreatedAt())
	                .updatedAt(payment.getUpdatedAt())
	                .build();
	}
}

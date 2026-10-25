
package com.pc.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.pc.event.PaymentSuccessEvent;
import com.pc.payload.request.EmailNotificationRequest;
import com.pc.service.INotificationService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PaymentEventConsumer {

    private final INotificationService notificationService;
	
	@PostConstruct
	public void checkConsumerBean() {
	    System.out.println("PaymentEventConsumer bean created");
	}

    @KafkaListener(
        topics = "payment-success",
        groupId = "notification-service"
    )
    public void consumePaymentSuccess(PaymentSuccessEvent event) {

        System.out.println("========== KAFKA EVENT RECEIVED ==========");
        System.out.println("Payment ID: " + event.getPaymentId());
        System.out.println("Booking ID: " + event.getBookingId());
        System.out.println("Amount: " + event.getAmount());
        System.out.println("Status: " + event.getStatus());
        System.out.println("Payment Time: " + event.getPaymentTime());
        System.out.println("==========================================");
        
        EmailNotificationRequest request = EmailNotificationRequest.builder()
                .to(event.getEmail())
                .subject("Payment Successful - Airline Booking System")
                .message("Hello " + event.getFullName() + ",\n\n"
                        + "Your payment was successful!\n\n"
                        + "Payment ID: " + event.getPaymentId() + "\n"
                        + "Booking ID: " + event.getBookingId() + "\n"
                        + "Amount: INR " + event.getAmount() + "\n"
                        + "Status: " + event.getStatus() + "\n"
                        + "Payment Time: " + event.getPaymentTime() + "\n\n"
                        + "Thank you for booking with us.")
                .build();

        notificationService.sendEmail(request);
    }
}

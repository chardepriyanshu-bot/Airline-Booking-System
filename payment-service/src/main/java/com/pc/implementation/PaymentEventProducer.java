package com.pc.implementation;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.pc.event.PaymentSuccessEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentEventProducer {
	
	private static final String TOPIC="payment-success";
	
	private final KafkaTemplate<String, PaymentSuccessEvent> kafkaTemplate;
	
	public void publicPaymentSuccess(PaymentSuccessEvent event) {
		kafkaTemplate.send(TOPIC,event);
	}

}

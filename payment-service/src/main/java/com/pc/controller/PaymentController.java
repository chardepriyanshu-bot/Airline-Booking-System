
package com.pc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pc.payload.request.PaymentRequest;
import com.pc.payload.request.PaymentVerificationRequest;
import com.pc.payload.response.PaymentResponse;
import com.pc.payload.response.RazorpayOrderResponse;
import com.pc.service.IPaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Validated
public class PaymentController {

	private final IPaymentService paymentService;

	@PostMapping
	public ResponseEntity<RazorpayOrderResponse> initiatePayment(@Valid @RequestBody PaymentRequest request) {

		RazorpayOrderResponse response = paymentService.initiatePayment(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{paymentId}")
	public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long paymentId) {

		return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
	}

	@GetMapping("/booking/{bookingId}")
	public ResponseEntity<List<PaymentResponse>> getPaymentsByBookingId(@PathVariable Long bookingId) {

		return ResponseEntity.ok(paymentService.getPaymentsByBookingId(bookingId));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PaymentResponse>> getPaymentsByUserId(@PathVariable Long userId) {

		return ResponseEntity.ok(paymentService.getPaymentsByUserId(userId));
	}
	
	@PostMapping("/verify")
	public ResponseEntity<PaymentResponse> verifyPayment(
	        @Valid @RequestBody PaymentVerificationRequest request) {

	    return ResponseEntity.ok(
	            paymentService.verifyPayment(request));
	}
}

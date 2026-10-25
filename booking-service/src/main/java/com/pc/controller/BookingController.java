package com.pc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.pc.payload.request.BookingRequest;
import com.pc.payload.response.BookingResponse;
import com.pc.service.IBookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {

	private final IBookingService bookingService;
	
	@Value("${internal.api.token}")
	private String internalApiToken;

	@PostMapping
	public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {

		BookingResponse response = bookingService.createBooking(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{bookingId}")
	public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long bookingId) {

		return ResponseEntity.ok(bookingService.getBookingById(bookingId));
	}

	@GetMapping("/reference/{bookingReference}")
	public ResponseEntity<BookingResponse> getBookingByReference(@PathVariable String bookingReference) {

		return ResponseEntity.ok(bookingService.getBookingByReference(bookingReference));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<BookingResponse>> getBookingsByUserId(@PathVariable Long userId) {

		return ResponseEntity.ok(bookingService.getBookingsByUserId(userId));
	}

	@PatchMapping("/{bookingId}/cancel")
	public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long bookingId) {

		return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
	}

	@PatchMapping("/{bookingId}/confirm-payment")
	public ResponseEntity<BookingResponse> confirmBookingAfterPayment(@PathVariable Long bookingId) {

		return ResponseEntity.ok(bookingService.confirmBookingAfterPayment(bookingId));
	}

	@PatchMapping("/{bookingId}/confirm-payment-with-id")
	public ResponseEntity<BookingResponse> confirmBookingAfterPayment(@PathVariable Long bookingId,
			@RequestHeader("X-Internal-Token") String token) {

		if (!internalApiToken.equals(token)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid internal token");
		}

		return ResponseEntity.ok(bookingService.confirmBookingAfterPayment(bookingId));
	}
}
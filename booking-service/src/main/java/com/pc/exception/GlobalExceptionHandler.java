package com.pc.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BookingNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleBookingNotFound(BookingNotFoundException ex,
			HttpServletRequest request) {

		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
	}

	@ExceptionHandler(BookingNotFoundByReferenceException.class)
	public ResponseEntity<Map<String, Object>> handleBookingNotFoundByReference(BookingNotFoundException ex,
			HttpServletRequest request) {

		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException ex,
			HttpServletRequest request) {

		return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		String message = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage()).findFirst()
				.orElse("Invalid request.");

		return buildResponse(HttpStatus.BAD_REQUEST, message, request);
	}

	private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message,
			HttpServletRequest request) {

		Map<String, Object> body = Map.of("timestamp", LocalDateTime.now(), "status", status.value(), "error",
				status.getReasonPhrase(), "message", message, "path", request.getRequestURI());

		return ResponseEntity.status(status).body(body);
	}
}
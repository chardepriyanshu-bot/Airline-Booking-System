
package com.pc.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(PaymentNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handlePaymentNotFound(PaymentNotFoundException ex) {

		Map<String, Object> error = new HashMap<>();

		error.put("timestamp", LocalDateTime.now());
		error.put("status", HttpStatus.NOT_FOUND.value());
		error.put("error", "Payment Not Found");
		error.put("message", ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {

		Map<String, Object> error = new HashMap<>();

		error.put("timestamp", LocalDateTime.now());
		error.put("status", HttpStatus.NOT_FOUND.value());
		error.put("error", "Payment Not Found");
		error.put("message", ex.getMessage());

		return ResponseEntity.ok(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {

		Map<String, String> validationErrors = new HashMap<>();

		ex.getBindingResult().getFieldErrors()
				.forEach(fieldError -> validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage()));

		Map<String, Object> error = new HashMap<>();

		error.put("timestamp", LocalDateTime.now());
		error.put("status", HttpStatus.BAD_REQUEST.value());
		error.put("error", "Validation Failed");
		error.put("messages", validationErrors);

		return ResponseEntity.badRequest().body(error);
	}
}

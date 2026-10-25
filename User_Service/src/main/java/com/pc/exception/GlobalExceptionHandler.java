package com.pc.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InvalidUserRoleException.class)
	public ResponseEntity<Map<String, Object>> handlerInvalidUserRoleException(InvalidUserRoleException e){
		
		Map<String, Object> map=new HashMap<>();
		map.put("timestamp", LocalDateTime.now());
		map.put("status", HttpStatus.UNAUTHORIZED.value());
		map.put("error", "Invalid Role");
		map.put("message", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handlerUserNotFoundException(UserNotFoundException e){
		
		Map<String, Object> map=new HashMap<>();
		map.put("timestamp", LocalDateTime.now());
		map.put("status", HttpStatus.NOT_FOUND.value());
		map.put("error", "Not Found");
		map.put("message", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	}
}

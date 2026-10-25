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
	
	@ExceptionHandler(AircraftNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handlerAircraftNotFoundException(AircraftNotFoundException e){
		Map<String, Object> map=new HashMap<>();
		map.put("timestamp", LocalDateTime.now());
		map.put("status", HttpStatus.NOT_FOUND.value());
		map.put("error", "Not Found");
		map.put("message", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	}
	
	@ExceptionHandler(AircraftAlreadyExistException.class)
	public ResponseEntity<Map<String, Object>> handlerAircraftAlreadyExistException(AircraftAlreadyExistException e){
		Map<String, Object> map=new HashMap<>();
		map.put("timestamp", LocalDateTime.now());
		map.put("status", HttpStatus.ALREADY_REPORTED.value());
		map.put("error", "Already Exist");
		map.put("message", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(map);
	}
	
	@ExceptionHandler(AirlineNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handlerAirlineNotFoundException(AirlineNotFoundException e){
		Map<String, Object> map=new HashMap<>();
		map.put("timestamp", LocalDateTime.now());
		map.put("status", HttpStatus.NOT_FOUND.value());
		map.put("error", "Not Found");
		map.put("message", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	}
}

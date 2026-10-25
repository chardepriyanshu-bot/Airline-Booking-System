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
	
	@ExceptionHandler(AirportNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handlerAirportNotFoundException(AirportNotFoundException e){
		
		Map<String, Object> map=new HashMap<>();
		map.put("timestamp", LocalDateTime.now());
		map.put("status", HttpStatus.NOT_FOUND.value());
		map.put("error", "Not Found");
		map.put("message", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	}
	
	@ExceptionHandler(CityNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handlerAirportNotFoundException(CityNotFoundException e){
		
		Map<String, Object> map=new HashMap<>();
		map.put("timestamp", LocalDateTime.now());
		map.put("status", HttpStatus.NOT_FOUND.value());
		map.put("error", "Not Found");
		map.put("message", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	}
	
	@ExceptionHandler(CityAlreadyExistException.class)
	public ResponseEntity<Map<String, Object>> handlerCityAlreadyExistException(CityAlreadyExistException e){
		
		Map<String, Object> map=new HashMap<>();
		map.put("timestamp", LocalDateTime.now());
		map.put("status", HttpStatus.ALREADY_REPORTED.value());
		map.put("error", "Already Exist");
		map.put("message", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(map);
	}
	
	@ExceptionHandler(AirportAlreadyExistException.class)
	public ResponseEntity<Map<String, Object>> handlerAirportAlreadyExistException(AirportAlreadyExistException e){
		
		Map<String, Object> map=new HashMap<>();
		map.put("timestamp", LocalDateTime.now());
		map.put("status", HttpStatus.ALREADY_REPORTED.value());
		map.put("error", "Already Exist");
		map.put("message", e.getMessage());
		
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(map);
	}
}

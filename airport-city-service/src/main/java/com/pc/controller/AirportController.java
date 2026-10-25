package com.pc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pc.payload.request.AirportRequest;
import com.pc.payload.response.AirportResponse;
import com.pc.service.IAirportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/airports")
@Validated
public class AirportController {
	
	private final IAirportService airportService;
	
	@PostMapping("/create")
	public ResponseEntity<AirportResponse> createAirport(@Valid @RequestBody AirportRequest request){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(airportService.createAirport(request));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AirportResponse> getAirportById(@PathVariable Long id){
		
		return ResponseEntity.ok(airportService.getAirportById(id));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<AirportResponse>> getAirportById(){
		
		return ResponseEntity.ok(airportService.getAllAirport());
	}
	
	@GetMapping("/city/{id}")
	public ResponseEntity<List<AirportResponse>> getAirportByCityId(@PathVariable Long id){
		
		return ResponseEntity.ok(airportService.getAirportByCityId(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AirportResponse> updateAirport(@PathVariable Long id, 
			@Valid @RequestBody AirportRequest request){
		return ResponseEntity.ok(airportService.updateAirport(id, request));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAirport(@PathVariable Long id){
		airportService.deleteAirport(id);
		return ResponseEntity.ok("Airport is successfully with id: "+id);
	}
}

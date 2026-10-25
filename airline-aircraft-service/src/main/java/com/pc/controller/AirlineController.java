package com.pc.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pc.enums.AirlineStatus;
import com.pc.payload.request.AirlineRequest;
import com.pc.payload.response.AirlineResponse;
import com.pc.service.IAirlineService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/airline")
@RequiredArgsConstructor
@Validated
public class AirlineController {
	
	private final IAirlineService airlineService;
	
	@PostMapping("/create")
	public ResponseEntity<AirlineResponse> createAirline(@Valid @RequestBody AirlineRequest request){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(airlineService.createAirline(request));
	}
	
	@GetMapping("/{airlineId}")
	public ResponseEntity<AirlineResponse> getAirlineByAirlineId(@PathVariable Long airlineId){
		
		return ResponseEntity.ok(airlineService.getAirlineById(airlineId));
	}
	
	@GetMapping("owner/{ownerId}")
	public ResponseEntity<AirlineResponse> getAirlineByOwnerId(@PathVariable Long ownerId){
		
		return ResponseEntity.ok(airlineService.getAirlineByOwnerId(ownerId));
	}
	
	@GetMapping
	public ResponseEntity<Page<AirlineResponse>> getAllAirlines(Pageable pageable){
		
		return ResponseEntity.ok(airlineService.getAllAirlines(pageable));
	}
	
	@PutMapping
	public ResponseEntity<AirlineResponse> updateAirline(@Valid @RequestBody AirlineRequest airlineRequest,
			@RequestHeader("X-User-Id") Long userId){
		return ResponseEntity.ok(airlineService.updateAirline(airlineRequest, userId));	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAirline(@PathVariable Long id,@RequestHeader("X-User-Id") Long userId){
		airlineService.deleteAirline(id,userId);
		return ResponseEntity.ok("Airline is a successfully deleted.");
	}
	
	@PutMapping("/{id}/approve")
	public ResponseEntity<AirlineResponse> approveAirline(
			@PathVariable Long id){
		return ResponseEntity.ok(airlineService.changeAirlineStatus(id, AirlineStatus.ACTIVE));	
	}
	
	@PutMapping("/{id}/suspend")
	public ResponseEntity<AirlineResponse> suspendAirline(
			@PathVariable Long id){
		return ResponseEntity.ok(airlineService.changeAirlineStatus(id, AirlineStatus.INACTIVE));	
	}
	
	@PutMapping("/{id}/ban")
	public ResponseEntity<AirlineResponse> banAirline(
			@PathVariable Long id){
		return ResponseEntity.ok(airlineService.changeAirlineStatus(id, AirlineStatus.BANNED));	
	}
}

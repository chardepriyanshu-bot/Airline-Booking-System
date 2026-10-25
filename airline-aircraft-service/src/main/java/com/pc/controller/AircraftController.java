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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pc.payload.request.AircraftRequest;
import com.pc.payload.response.AircraftResponse;
import com.pc.service.IAircraftService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/aircraft")
@RequiredArgsConstructor
@Validated
public class AircraftController {
	
	private final IAircraftService aircraftService;
	
	@PostMapping
	public ResponseEntity<AircraftResponse> createAircraft(@Valid @RequestBody AircraftRequest aircraftRequest,
			@RequestHeader("X-User-Id") Long ownerId){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(aircraftService.createAircraft(aircraftRequest, ownerId));
	}
	
	@GetMapping
	public ResponseEntity<List<AircraftResponse>> getAllAircraft(@RequestHeader("X-User-Id") Long ownerId){
		return ResponseEntity.ok(aircraftService.getAllAircraftsByOwner(ownerId));
	}
	
	@GetMapping("/{aircraftId}")
	public ResponseEntity<AircraftResponse> getAircraftById(@PathVariable Long aircraftId){
		return ResponseEntity.ok(aircraftService.getAircraftById(aircraftId));
	}
	
	@PutMapping("/{aircraftId}")
	public ResponseEntity<AircraftResponse> updateAircraft(@PathVariable Long aircraftId, @Valid @RequestBody AircraftRequest aircraftRequest,
			@RequestHeader("X-User-Id") Long ownerId){
		return ResponseEntity.ok(aircraftService.updateAircraft(aircraftId,aircraftRequest, ownerId));
	}
	
	@DeleteMapping("/{aircraftId}")
	public ResponseEntity<String> deleteAircraft(@PathVariable Long aircraftId, @RequestHeader("X-User-Id") Long ownerId){
		aircraftService.deleteAircraftById(aircraftId, ownerId);
		
		return ResponseEntity.ok("Aircraft is a successfully deleted.");
	}
	
}

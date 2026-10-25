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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pc.enums.FlightStatus;
import com.pc.payload.request.FlightRequest;
import com.pc.payload.response.FlightResponse;
import com.pc.service.IFlightService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flight")
@Validated
public class FlightController {
	
	private final IFlightService flightService;
	
	@PostMapping
	public ResponseEntity<FlightResponse> createFlight(@Valid @RequestBody FlightRequest request){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(flightService.createFlight(request));
	}
	
	@GetMapping("{flightId}")
	public ResponseEntity<FlightResponse> getFlightById(@PathVariable Long flightId){
		
		return ResponseEntity.ok(flightService.getByFlightId(flightId));
	}
	
	@GetMapping("aircraft/{aircraftId}")
	public ResponseEntity<List<FlightResponse>> getFlightByAircraftId(@PathVariable Long aircraftId){
		
		return ResponseEntity.ok(flightService.getFlightsByAircraftId(aircraftId));
	}
	
	@GetMapping("airline/{airlineId}")
	public ResponseEntity<List<FlightResponse>> getFlightByAirlineId(@PathVariable Long airlineId){
		
		return ResponseEntity.ok(flightService.getFlightsByAirlineId(airlineId));
	}
	
	@GetMapping("destination/{airportId}")
	public ResponseEntity<List<FlightResponse>> getFlightByDestinationAirportId(@PathVariable Long airportId){
		
		return ResponseEntity.ok(flightService.getFlightsByDestinationAirportId(airportId));
	}
	
	@GetMapping("source/{airportId}")
	public ResponseEntity<List<FlightResponse>> getFlightBySourceAirportId(@PathVariable Long airportId){
		
		return ResponseEntity.ok(flightService.getFlightsBySourceAirportId(airportId));
	}
	
	@GetMapping
	public ResponseEntity<List<FlightResponse>> getAllFlights(){
		
		return ResponseEntity.ok(flightService.getAllFlights());
	}
	
	@PutMapping("{flightId}")
	public ResponseEntity<FlightResponse> updateFlight(@PathVariable Long flightId, @Valid @RequestBody FlightRequest request){
		
		return ResponseEntity.ok(flightService.updateFlight(flightId,request));
	}
	
	@PutMapping("status/{flightId}")
	public ResponseEntity<FlightResponse> changeFlightStatus(@PathVariable Long flightId, @RequestParam FlightStatus status){
		
		return ResponseEntity.ok(flightService.changeFlightStatus(flightId,status));
	}
	
	@DeleteMapping("{flightId}")
	public ResponseEntity<String> deleteFlight(@PathVariable Long flightId){
		
		flightService.deleteFlight(flightId);
		
		return ResponseEntity.ok("Flight is a successfully with id "+flightId);
	}
}

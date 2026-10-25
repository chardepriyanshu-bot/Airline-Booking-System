package com.pc.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pc.payload.request.FlightScheduleRequest;
import com.pc.payload.request.FlightSearchRequest;
import com.pc.payload.response.FlightScheduleResponse;
import com.pc.payload.response.FlightSearchResponse;
import com.pc.service.IFlightScheduleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/flight-schedules")
@RequiredArgsConstructor
@Validated
public class FlightScheduleController {

    private final IFlightScheduleService flightScheduleService;

    @PostMapping
    public ResponseEntity<FlightScheduleResponse> createFlightSchedule(
            @Valid @RequestBody FlightScheduleRequest request) {

        FlightScheduleResponse response =
                flightScheduleService.createFlightSchedule(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<FlightScheduleResponse> getFlightScheduleById(
            @PathVariable Long scheduleId) {

        return ResponseEntity.ok(
                flightScheduleService.getFlightScheduleById(scheduleId));
    }

    @GetMapping
    public ResponseEntity<List<FlightScheduleResponse>> getAllFlightSchedules() {

        return ResponseEntity.ok(
                flightScheduleService.getAllFlightSchedules());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<FlightScheduleResponse>> getFlightSchedules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                flightScheduleService.getFlightSchedules(pageable));
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<FlightScheduleResponse> updateFlightSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody FlightScheduleRequest request) {

        return ResponseEntity.ok(
                flightScheduleService.updateFlightSchedule(scheduleId, request));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<String> deleteFlightSchedule(
            @PathVariable Long scheduleId) {

        flightScheduleService.deleteFlightSchedule(scheduleId);

        return ResponseEntity.ok("Flight schedule is cancelled "+scheduleId);
    }
    

	@PostMapping("/search")
	public ResponseEntity<List<FlightSearchResponse>> searchFlights(
	        @Valid @RequestBody FlightSearchRequest request) {
	
	    List<FlightSearchResponse> responses =
	            flightScheduleService.searchFlights(request);
	
	    return ResponseEntity.ok(responses);
	}

}
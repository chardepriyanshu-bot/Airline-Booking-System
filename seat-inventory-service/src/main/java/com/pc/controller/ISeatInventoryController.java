package com.pc.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pc.payload.request.SeatInventoryRequest;
import com.pc.payload.response.SeatInventoryResponse;
import com.pc.service.ISeatInventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/seat-inventory")
public class ISeatInventoryController {

	private final ISeatInventoryService seatInventoryService;

	@PostMapping
	public SeatInventoryResponse createInventory(@RequestBody SeatInventoryRequest request) {
		return seatInventoryService.createInventory(request);
	}

	@GetMapping("/{scheduleId}")
	public SeatInventoryResponse getInventory(@PathVariable Long scheduleId) {

		return seatInventoryService.getInventoryByScheduleId(scheduleId);
	}

	@PostMapping("/{scheduleId}/reserve")
	public SeatInventoryResponse reserveSeats(@PathVariable Long scheduleId, @RequestParam Integer numberOfSeats) {

		return seatInventoryService.reserveSeats(scheduleId, numberOfSeats);
	}

	@PostMapping("/{scheduleId}/release")
	public SeatInventoryResponse releaseSeats(@PathVariable Long scheduleId, @RequestParam Integer numberOfSeats) {

		return seatInventoryService.releaseSeats(scheduleId, numberOfSeats);
	}
}

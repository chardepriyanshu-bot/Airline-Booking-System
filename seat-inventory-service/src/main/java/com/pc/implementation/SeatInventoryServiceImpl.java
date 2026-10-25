package com.pc.implementation;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pc.client.IAircraftServiceClient;
import com.pc.client.IFlightScheduleServiceClient;
import com.pc.client.IFlightServiceClient;
import com.pc.exception.SeatInventoryNotFoundException;
import com.pc.mapper.SeatInventoryMapper;
import com.pc.model.SeatInventory;
import com.pc.payload.request.SeatInventoryRequest;
import com.pc.payload.response.AircraftResponse;
import com.pc.payload.response.FlightResponse;
import com.pc.payload.response.FlightScheduleResponse;
import com.pc.payload.response.SeatInventoryResponse;
import com.pc.repository.ISeatInventoryRepository;
import com.pc.service.ISeatInventoryService;

import lombok.RequiredArgsConstructor;
import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class SeatInventoryServiceImpl implements ISeatInventoryService {

	private final ISeatInventoryRepository seatInventoryRepository;
	private final IFlightScheduleServiceClient flightScheduleServiceClient;
	private final IAircraftServiceClient aircraftServiceClient;
	private final IFlightServiceClient flightServiceClient;
	private final StringRedisTemplate redisTemplate;
	private final ObjectMapper objectMapper;

	@Override
	@Transactional
	public SeatInventoryResponse createInventory(SeatInventoryRequest request) {

		if (seatInventoryRepository.existsByScheduleId(request.getScheduleId()))
			throw new IllegalArgumentException(
					"Seat inventory already exists for schedule ID: " + request.getScheduleId());

		FlightScheduleResponse scheduleService = flightScheduleServiceClient
				.getFlightScheduleById(request.getScheduleId());

		FlightResponse flight = flightServiceClient.getFlightByResponse(scheduleService.getFlightId());

		AircraftResponse aircraft = aircraftServiceClient.getAircraftById(flight.getAircraftId());

		Integer totalSeats = aircraft.getTotalSeats();

		SeatInventory inventory = SeatInventory.builder().scheduleId(request.getScheduleId()).totalSeats(totalSeats)
				.bookedSeats(0).availableSeats(totalSeats).build();

		SeatInventory savedInventory = seatInventoryRepository.save(inventory);
		
		//Clear the any cache for this scheduled Id
		evictInventoryCache(request.getScheduleId());

		return SeatInventoryMapper.mapToResponse(savedInventory);
	}

	@Override
	@Transactional(readOnly = true)
	public SeatInventoryResponse getInventoryByScheduleId(Long scheduleId) {

	    String cacheKey = "seat-inventory:" + scheduleId;

	    // Check Redis first
	    String cachedData = redisTemplate.opsForValue().get(cacheKey);

	    if (cachedData != null) {
	        try {
	            SeatInventoryResponse response =
	                    objectMapper.readValue(
	                            cachedData, SeatInventoryResponse.class);

	            System.out.println("Redis Cache HIT: " + cacheKey);
	            return response;

	        } catch (Exception e) {
	            // Invalid cache data: remove it and fetch from database
	            redisTemplate.delete(cacheKey);
	        }
	    }

	    System.out.println("Redis Cache MISS: " + cacheKey);

	    SeatInventory inventory = seatInventoryRepository
	            .findByScheduleId(scheduleId)
	            .orElseThrow(() -> new SeatInventoryNotFoundException(
	                    "Seat inventory not found for schedule ID: " + scheduleId));

	    SeatInventoryResponse response =
	            SeatInventoryMapper.mapToResponse(inventory);

	    try {
	        String json = objectMapper.writeValueAsString(response);

	        redisTemplate.opsForValue().set(
	                cacheKey, json, Duration.ofMinutes(5));

	    } catch (Exception e) {
	        // Cache failure should not prevent a successful database response
	        System.err.println("Redis cache write failed: " + e.getMessage());
	    }

	    return response;
	}
	@Override
	@Transactional
	public SeatInventoryResponse reserveSeats(Long scheduleId, Integer numberOfSeats) {

		if (numberOfSeats == null || numberOfSeats <= 0)
			throw new IllegalArgumentException("Number of seats must be greater than zero");

		SeatInventory inventory = seatInventoryRepository.findByScheduleIdForUpdate(scheduleId).orElseThrow(
				() -> new SeatInventoryNotFoundException("Seat inventory not found for schedule ID: " + scheduleId));

		if (inventory.getAvailableSeats() < numberOfSeats)
			throw new IllegalArgumentException("Not enough available seats");

		inventory.setBookedSeats(inventory.getBookedSeats() + numberOfSeats);

		inventory.setAvailableSeats(inventory.getAvailableSeats() - numberOfSeats);

		SeatInventory savedInventory = seatInventoryRepository.save(inventory);
		
		//Invalidate cached inventory after reservation
		evictInventoryCache(scheduleId);

		return SeatInventoryMapper.mapToResponse(savedInventory);
	}

	@Override
	@Transactional
	public SeatInventoryResponse releaseSeats(Long scheduleId, Integer numberOfSeats) {

		if (numberOfSeats == null || numberOfSeats <= 0)
			throw new IllegalArgumentException("Number of seats must be greater than zero");

		SeatInventory inventory = seatInventoryRepository.findByScheduleIdForUpdate(scheduleId).orElseThrow(
				() -> new SeatInventoryNotFoundException("Seat inventory not found for schedule ID: " + scheduleId));
		
		if (inventory.getBookedSeats() < numberOfSeats)
		    throw new IllegalArgumentException(
		            "Cannot release more seats than currently booked");
		
		inventory.setBookedSeats(inventory.getBookedSeats() - numberOfSeats);

		inventory.setAvailableSeats(inventory.getAvailableSeats() + numberOfSeats);

		SeatInventory savedInventory = seatInventoryRepository.save(inventory);
		
		evictInventoryCache(scheduleId);

		return SeatInventoryMapper.mapToResponse(savedInventory);
	}
	
	private void evictInventoryCache(Long scheduleId) {

	    String cacheKey = "seat-inventory:" + scheduleId;

	    if (TransactionSynchronizationManager.isSynchronizationActive()) {

	        TransactionSynchronizationManager.registerSynchronization(
	                new TransactionSynchronization() {
	                    @Override
	                    public void afterCommit() {
	                        redisTemplate.delete(cacheKey);
	                    }
	                });

	    } else {
	        redisTemplate.delete(cacheKey);
	    }
	}
}

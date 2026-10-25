package com.pc.service;

import com.pc.payload.request.SeatInventoryRequest;
import com.pc.payload.response.SeatInventoryResponse;

public interface ISeatInventoryService {
	  
	public SeatInventoryResponse createInventory(SeatInventoryRequest request);

	public SeatInventoryResponse getInventoryByScheduleId(Long scheduleId);
	
	public SeatInventoryResponse reserveSeats(Long scheduleId, Integer numberOfSeats);

	public SeatInventoryResponse releaseSeats(Long scheduleId, Integer numberOfSeats);
	
}

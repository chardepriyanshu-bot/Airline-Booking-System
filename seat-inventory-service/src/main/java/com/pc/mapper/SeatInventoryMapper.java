package com.pc.mapper;

import com.pc.model.SeatInventory;
import com.pc.payload.response.SeatInventoryResponse;

public class SeatInventoryMapper {
	
	public static SeatInventoryResponse mapToResponse(SeatInventory inventory) {
		return SeatInventoryResponse.builder()
                .inventoryId(inventory.getInventoryId())
                .scheduleId(inventory.getScheduleId())
                .totalSeats(inventory.getTotalSeats())
                .bookedSeats(inventory.getBookedSeats())
                .availableSeats(inventory.getAvailableSeats())
                .version(inventory.getVersion())
                .build();
	}
}

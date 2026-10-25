package com.pc.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatInventoryResponse {

    private Long inventoryId;

    private Long scheduleId;

    private Integer totalSeats;

    private Integer bookedSeats;

    private Integer availableSeats;

    private Long version;
}

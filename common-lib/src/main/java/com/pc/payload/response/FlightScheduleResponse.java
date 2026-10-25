package com.pc.payload.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.pc.enums.ScheduleStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightScheduleResponse {

    private Long scheduleId;

    private Long flightId;

    private LocalDateTime departureDateTime;

    private LocalDateTime arrivalDateTime;

    private String departureTerminal;

    private String arrivalTerminal;

    private ScheduleStatus scheduleStatus;

    private BigDecimal baseFare;

    private Long version;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
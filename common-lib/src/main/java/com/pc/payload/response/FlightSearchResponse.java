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
public class FlightSearchResponse {

	  private Long scheduleId;

	    private Long flightId;

	    private Long sourceAirportId;

	    private Long destinationAirportId;

	    private LocalDateTime departureDateTime;

	    private LocalDateTime arrivalDateTime;

	    private String departureTerminal;

	    private String arrivalTerminal;

	    private BigDecimal baseFare;

	    private ScheduleStatus scheduleStatus;
}


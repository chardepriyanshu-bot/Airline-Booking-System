package com.pc.payload.response;

import java.time.LocalDateTime;

import com.pc.enums.FlightStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightResponse {
	private Long flightId;
	private String flightName;
	private Long airlineId;
	private Long aircraftId;
	private Long sourceAirportId;
	private Long destinationAirportId;
	private FlightStatus flightStatus;
	private Integer version;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}

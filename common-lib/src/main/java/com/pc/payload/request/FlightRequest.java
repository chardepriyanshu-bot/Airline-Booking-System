package com.pc.payload.request;

import com.pc.enums.FlightStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FlightRequest {
	 
	@NotBlank(message = "Flight Name is required.")
	private String flightName;

	@NotNull
	private Long airlineId;

	@NotNull
	private Long aircraftId;

	@NotNull
	private Long sourceAirportId;
	
	@NotNull
	private Long destinationAirportId;
	
	@Valid
	private FlightStatus flightStatus;
}

package com.pc.payload.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightSearchRequest {

    @NotNull(message = "Source airport ID is required.")
    private Long sourceAirportId;

    @NotNull(message = "Destination airport ID is required.")
    private Long destinationAirportId;

    @NotNull(message = "Departure date is required.")
    private LocalDate departureDate;

    @Min(value = 1, message = "Passengers must be at least 1.")
    private Integer passengers;
}

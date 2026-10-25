package com.pc.payload.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.pc.enums.ScheduleStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightScheduleRequest {

    @NotNull(message = "Flight ID is required.")
    private Long flightId;

    @NotNull(message = "Departure date and time are required.")
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival date and time are required.")
    private LocalDateTime arrivalDateTime;

    @Size(max = 20, message = "Departure terminal cannot exceed 20 characters.")
    private String departureTerminal;

    @Size(max = 20, message = "Arrival terminal cannot exceed 20 characters.")
    private String arrivalTerminal;

    @NotNull(message = "Schedule status is required.")
    private ScheduleStatus scheduleStatus;

    @NotNull(message = "Base fare is required.")
    @DecimalMin(value = "0.01", message = "Base fare must be greater than zero.")
    private BigDecimal baseFare;
}
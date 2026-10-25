package com.pc.payload.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
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
public class BookingRequest {

    @NotNull(message = "User ID is required.")
    private Long userId;

    @NotNull(message = "Schedule ID is required.")
    private Long scheduleId;

    @NotNull(message = "Passenger count is required.")
    @Min(value = 1, message = "At least one passenger is required.")
    private Integer passengerCount;

    @NotNull(message = "Total amount is required.")
    @DecimalMin(value = "0.01", message = "Total amount must be greater than zero.")
    private BigDecimal totalAmount;
}
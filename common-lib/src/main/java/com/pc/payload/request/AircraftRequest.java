package com.pc.payload.request;

import java.time.LocalDate;

import com.pc.enums.AircraftStatus;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AircraftRequest {

    @NotBlank(message = "Aircraft code is required")
    @Size(max = 20, message = "Code must be at most 20 characters")
    private String code;

    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;

    @NotNull(message = "Seating capacity is required")
    @Positive(message = "Seating capacity must be positive")
    private Integer seatingCapacity;

    @Builder.Default
    @PositiveOrZero(message = "Economy seats cannot be negative")
    private Integer economySeats = 0;

    @Builder.Default
    @PositiveOrZero(message = "Premium economy seats cannot be negative")
    private Integer premiumEconomySeats = 0;

    @Builder.Default
    @PositiveOrZero(message = "Business seats cannot be negative")
    private Integer businessSeats = 0;

    @Builder.Default
    @PositiveOrZero(message = "First class seats cannot be negative")
    private Integer firstClassSeats = 0;

    @Positive(message = "Range must be positive")
    private Integer rangeKm;

    @Positive(message = "Speed must be positive")
    private Integer cruisingSpeedKm;

    @Positive(message = "Altitude must be positive")
    private Double maxAltitudeKm;

    @NotNull(message = "Year of manufacture is required")
    @PastOrPresent(message = "Year of manufacture cannot be in the future")
    private LocalDate yearOfManufacture;

    @PastOrPresent(message = "Last maintenance date cannot be in the future")
    private LocalDate lastMaintenanceDate;

    @NotNull(message = "Aircraft status is required")
    private AircraftStatus aircraftStatus;

    @Builder.Default
    private Boolean isAvailable = true;

    @NotNull(message = "Airline id is required")
    private Long airlineId;

    private Long currentAirportId;

    @AssertTrue(message = "Sum of all class seats must equal seating capacity")
    private boolean isSeatTotalValid() {
        if (seatingCapacity == null) return true;
        return nonZero(economySeats) + nonZero(premiumEconomySeats)
                + nonZero(businessSeats) + nonZero(firstClassSeats) == seatingCapacity;
    }


    private static int nonZero(Integer v) {
        return v == null ? 0 : v;
    }
}
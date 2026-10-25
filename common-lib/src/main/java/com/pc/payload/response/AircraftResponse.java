package com.pc.payload.response;

import java.time.Instant;
import java.time.LocalDate;

import com.pc.enums.AircraftStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AircraftResponse {

    private Long aircraftId;
    private String code;
    private String model;
    private String manufacturer;

    private Integer seatingCapacity;
    private Integer economySeats;
    private Integer premiumEconomySeats;
    private Integer businessSeats;
    private Integer firstClassSeats;
    private Integer totalSeats;       

    private Integer rangeKm;
    private Integer cruisingSpeedKm;
    private Double maxAltitudeKm;

    private LocalDate yearOfManufacture;
    private LocalDate lastMaintenanceDate;

    private AircraftStatus aircraftStatus;
    private Boolean isAvailable;
    private Boolean operational;         

    private Long airlineId;
    private String airlineName;          
    private Long currentAirportId;

    private Instant createdAt;
    private Instant updatedAt;
}

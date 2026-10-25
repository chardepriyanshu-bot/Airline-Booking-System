package com.pc.mapper;

import java.time.Instant;

import com.pc.enums.AircraftStatus;
import com.pc.model.Aircraft;
import com.pc.model.Airline;
import com.pc.payload.request.AircraftRequest;
import com.pc.payload.response.AircraftResponse;

public class AircraftMapper {
	
	public static Aircraft toEntity(AircraftRequest req, Airline airline) {
	    return Aircraft.builder()
	            .code(req.getCode())
	            .model(req.getModel())
	            .manufacturer(req.getManufacturer())
	            .rangeKm(req.getRangeKm())
	            .economySeats((req.getEconomySeats()))
	            .premiumEconomySeats((req.getPremiumEconomySeats()))
	            .businessSeats((req.getBusinessSeats()))
	            .firstClassSeats((req.getFirstClassSeats()))
	            .cruisingSpeedkm(req.getCruisingSpeedKm())
	            .yearOfManufacture(req.getYearOfManufacture())
	            .lastMaintainanceDate(req.getLastMaintenanceDate())
	            .aircraftStatus(req.getAircraftStatus())
	            .isAvailable(req.getIsAvailable() == null ? true : req.getIsAvailable())
	            .airline(airline)
	            .seatingCapactiy(req.getSeatingCapacity())
	            .maxAltitudeKm(req.getMaxAltitudeKm())
	            .currentAirportId(req.getCurrentAirportId())
	            .createdAt(Instant.now())
	            .updateAt(Instant.now())
	            .build();
	}

	public static AircraftResponse toResponse(Aircraft a) {
	    return AircraftResponse.builder()
	            .aircraftId(a.getAircraftId())
	            .code(a.getCode())
	            .model(a.getModel())
	            .manufacturer(a.getManufacturer())
	            .totalSeats(a.getEconomySeats() + a.getPremiumEconomySeats()
	                      + a.getBusinessSeats() + a.getFirstClassSeats())
	            .economySeats(a.getEconomySeats())
	            .rangeKm(a.getRangeKm())
	            .seatingCapacity(a.getSeatingCapactiy())
	            .premiumEconomySeats(a.getPremiumEconomySeats())
	            .businessSeats(a.getBusinessSeats())
	            .firstClassSeats(a.getFirstClassSeats())
	            .cruisingSpeedKm(a.getCruisingSpeedkm())
	            .yearOfManufacture(a.getYearOfManufacture())
	            .lastMaintenanceDate(a.getLastMaintainanceDate())
	            .aircraftStatus(a.getAircraftStatus())
	            .isAvailable(a.getIsAvailable())
	            .operational(AircraftStatus.ACTIVE.equals(a.getAircraftStatus())
	                      && Boolean.TRUE.equals(a.getIsAvailable()))
	            .airlineId(a.getAirline() != null ? a.getAirline().getAirlineId() : null)
	            .airlineName(a.getAirline() != null ? a.getAirline().getName() : null)
	            .currentAirportId(a.getCurrentAirportId())
	            .maxAltitudeKm(a.getMaxAltitudeKm())
	            .createdAt(a.getCreatedAt())
	            .updatedAt(a.getUpdateAt())
	            .build();
	}

	public static Integer nz(Integer v) { return v == null ? 0 : v;}; 
	
	public static Aircraft toUpdate(AircraftRequest request, Aircraft aircraft) {
        aircraft.setCode(request.getCode());
        aircraft.setModel(request.getModel());
        aircraft.setManufacturer(request.getManufacturer());

        aircraft.setSeatingCapactiy(request.getSeatingCapacity());
        aircraft.setEconomySeats(nz(request.getEconomySeats()));
        aircraft.setPremiumEconomySeats(nz(request.getPremiumEconomySeats()));
        aircraft.setBusinessSeats(nz(request.getBusinessSeats()));
        aircraft.setFirstClassSeats(nz(request.getFirstClassSeats()));

        aircraft.setRangeKm(request.getRangeKm());
        aircraft.setCruisingSpeedkm(request.getCruisingSpeedKm());
        aircraft.setMaxAltitudeKm(request.getMaxAltitudeKm());

        aircraft.setYearOfManufacture(request.getYearOfManufacture());
        aircraft.setLastMaintainanceDate(request.getLastMaintenanceDate());

        aircraft.setAircraftStatus(request.getAircraftStatus());
        aircraft.setIsAvailable(request.getIsAvailable() == null ? Boolean.TRUE : request.getIsAvailable());

        aircraft.setCurrentAirportId(request.getCurrentAirportId());
        
        return aircraft;

	}
}

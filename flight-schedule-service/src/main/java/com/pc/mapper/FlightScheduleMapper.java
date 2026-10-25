package com.pc.mapper;

import com.pc.model.FlightSchedule;
import com.pc.payload.request.FlightScheduleRequest;
import com.pc.payload.response.FlightDetailsResponse;
import com.pc.payload.response.FlightScheduleResponse;
import com.pc.payload.response.FlightSearchResponse;

public class FlightScheduleMapper {

    public static FlightSchedule toEntity(FlightScheduleRequest request) {

        return FlightSchedule.builder()
                .flightId(request.getFlightId())
                .departureDateTime(request.getDepartureDateTime())
                .arrivalDateTime(request.getArrivalDateTime())
                .departureTerminal(request.getDepartureTerminal())
                .arrivalTerminal(request.getArrivalTerminal())
                .scheduleStatus(request.getScheduleStatus())
                .baseFare(request.getBaseFare())
                .build();
    }

    public static FlightScheduleResponse toResponse(FlightSchedule schedule) {

        return FlightScheduleResponse.builder()
                .scheduleId(schedule.getScheduleId())
                .flightId(schedule.getFlightId())
                .departureDateTime(schedule.getDepartureDateTime())
                .arrivalDateTime(schedule.getArrivalDateTime())
                .departureTerminal(schedule.getDepartureTerminal())
                .arrivalTerminal(schedule.getArrivalTerminal())
                .scheduleStatus(schedule.getScheduleStatus())
                .baseFare(schedule.getBaseFare())
                .version(schedule.getVersion())
                .createdAt(schedule.getCreatedAt())
                .updatedAt(schedule.getUpdatedAt())
                .build();
    }
    
    public static FlightSearchResponse mapToResponse(FlightSchedule schedule, FlightDetailsResponse flight) {
        return FlightSearchResponse.builder()
                .scheduleId(schedule.getScheduleId())
                .flightId(schedule.getFlightId())
                .departureDateTime(schedule.getDepartureDateTime())
                .arrivalDateTime(schedule.getArrivalDateTime())
                .departureTerminal(schedule.getDepartureTerminal())
                .arrivalTerminal(schedule.getArrivalTerminal())
                .baseFare(schedule.getBaseFare())
                .scheduleStatus(schedule.getScheduleStatus())
                .sourceAirportId(flight.getSourceAirportId())
                .destinationAirportId(flight.getDestinationAirportId())
                .build();
    }
}
package com.pc.mapper;

import com.pc.model.Flight;
import com.pc.payload.request.FlightRequest;
import com.pc.payload.response.FlightResponse;

public class FlightMapper {
	
	public static Flight toEntity(FlightRequest request) {
		if(request==null) 
			throw new IllegalArgumentException("Flight request shouldn't null");
		
		return Flight.builder()
				.flightName(request.getFlightName())
				.airlineId(request.getAirlineId())
				.aircraftId(request.getAircraftId())
				.sourceAirportId(request.getSourceAirportId())
				.destinationAirportId(request.getDestinationAirportId())
				.flightStatus(request.getFlightStatus())
				.build();	
	}
	
	public static FlightResponse toResponse(Flight flight) {
		if(flight==null)
			throw new IllegalArgumentException("Flight shouldn't null");
		
		return FlightResponse.builder()
				.flightId(flight.getFlightId())
				.flightName(flight.getFlightName())
				.airlineId(flight.getAirlineId())
				.aircraftId(flight.getAircraftId())
				.sourceAirportId(flight.getSourceAirportId())
				.destinationAirportId(flight.getDestinationAirportId())
				.flightStatus(flight.getFlightStatus())
				.version(flight.getVersion())
				.createdAt(flight.getCreatedAt())
				.updatedAt(flight.getUpdatedAt())
				.build();
	}
	
	
	public static Flight update(FlightRequest request){
		if(request==null) 
			throw new IllegalArgumentException("Flight request shouldn't null");
		
		return Flight.builder()
				.flightName(request.getFlightName())
				.airlineId(request.getAirlineId())
				.aircraftId(request.getAircraftId())
				.sourceAirportId(request.getSourceAirportId())
				.destinationAirportId(request.getDestinationAirportId())
				.flightStatus(request.getFlightStatus())
				.build();
				
	}
}

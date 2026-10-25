package com.pc.service;

import java.util.List;

import com.pc.enums.FlightStatus;
import com.pc.payload.request.FlightRequest;
import com.pc.payload.response.FlightResponse;

public interface IFlightService {
	public FlightResponse createFlight(FlightRequest flighRequest);
	public FlightResponse getByFlightId(Long flightId);
	public List<FlightResponse> getAllFlights();
	public FlightResponse updateFlight(Long flightId, FlightRequest flighRequest);
	public void deleteFlight(Long flightId);
	public List<FlightResponse> getFlightsByAirlineId(Long airlineId);
	public List<FlightResponse> getFlightsByAircraftId(Long aircraftId);
	public List<FlightResponse> getFlightsBySourceAirportId(Long airportId);
	public List<FlightResponse> getFlightsByDestinationAirportId(Long airportId);
	public FlightResponse changeFlightStatus(Long flightId, FlightStatus status);
}

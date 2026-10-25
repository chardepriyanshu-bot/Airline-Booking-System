package com.pc.implementation;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pc.enums.FlightStatus;
import com.pc.exception.FlightNotFoundException;
import com.pc.mapper.FlightMapper;
import com.pc.model.Flight;
import com.pc.payload.request.FlightRequest;
import com.pc.payload.response.FlightResponse;
import com.pc.repository.IFlightRepository;
import com.pc.service.IFlightService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements IFlightService{
	
	private final IFlightRepository flightRepository;
	
	@Override
	@Transactional
	public FlightResponse createFlight(FlightRequest flighRequest) {
		
		Flight flight = FlightMapper.toEntity(flighRequest);
		
		Flight savedFlight = flightRepository.saveAndFlush(flight);
		return FlightMapper.toResponse(savedFlight);
	}

	@Override
	public FlightResponse getByFlightId(Long flightId) {
		
		Flight flight = flightRepository.findById(flightId).orElseThrow(() -> 
					new FlightNotFoundException("Flight doesn't exist with id "+flightId));
		
		return FlightMapper.toResponse(flight);
	}

	@Override
	public List<FlightResponse> getAllFlights() {
		
		return flightRepository.findAll()
				.stream()
				.map(FlightMapper::toResponse)
				.toList();
	}

	@Override
	@Transactional
	public FlightResponse updateFlight(Long flightId, FlightRequest flighRequest) {
		
		Flight flight = flightRepository.findById(flightId).orElseThrow(() -> 
					new FlightNotFoundException("Flight doesn't exist with id "+flightId));
		
		Flight updatedFlight = FlightMapper.update(flighRequest);
		
		Flight savedFlight = flightRepository.save(updatedFlight);
		
		return FlightMapper.toResponse(savedFlight);
	}

	@Override
	public void deleteFlight(Long flightId) {
		flightRepository.deleteById(flightId);
	}

	@Override
	public List<FlightResponse> getFlightsByAirlineId(Long airlineId) {
		
		List<Flight> flightList = flightRepository.findByAirlineId(airlineId);
		
		if(flightList==null) 
			throw new FlightNotFoundException("Flights doesn't exist with airline id "+airlineId);
		
			return flightList
					.stream()
					.map(FlightMapper::toResponse)
					.toList();
	}

	@Override
	public List<FlightResponse> getFlightsByAircraftId(Long aircraftId) {
		
		List<Flight> flightList = flightRepository.findByAircraftId(aircraftId);
		
		if(flightList==null) 
			throw new FlightNotFoundException("Flights doesn't exist with aircraft id "+aircraftId);
		
			return flightList
					.stream()
					.map(FlightMapper::toResponse)
					.toList();
	}

	@Override
	public List<FlightResponse> getFlightsBySourceAirportId(Long airportId) {
		
		List<Flight> flightList = flightRepository.findBySourceAirportId(airportId);
		
		if(flightList==null) 
			throw new FlightNotFoundException("Flights doesn't exist with airport id "+airportId);
		
			return flightList
					.stream()
					.map(FlightMapper::toResponse)
					.toList();
	}

	@Override
	public List<FlightResponse> getFlightsByDestinationAirportId(Long airportId) {
		
		List<Flight> flightList = flightRepository.findByDestinationAirportId(airportId);
		
		if(flightList==null) 
			throw new FlightNotFoundException("Flights doesn't exist with airport id "+airportId);
		
			return flightList
					.stream()
					.map(FlightMapper::toResponse)
					.toList();
	}

	@Override
	@Transactional
	public FlightResponse changeFlightStatus(Long flightId, FlightStatus status) {
		
		Flight flight = flightRepository.findById(flightId).orElseThrow(() -> 
		new FlightNotFoundException("Flight doesn't exist with id "+flightId));
		
		flight.setFlightStatus(status);
		
		Flight savedFlight = flightRepository.save(flight);
		
		return FlightMapper.toResponse(savedFlight);
	}

}

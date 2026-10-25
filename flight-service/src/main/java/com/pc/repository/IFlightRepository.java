package com.pc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pc.model.Flight;
import java.util.List;


public interface IFlightRepository extends JpaRepository<Flight, Long> {
	
	List<Flight> findByAirlineId(Long airlineId);
	
	List<Flight> findByAircraftId(Long aircraftId);
	
	List<Flight> findBySourceAirportId(Long sourceAirportId);
	
	List<Flight> findByDestinationAirportId(Long destinationAirportId);
}

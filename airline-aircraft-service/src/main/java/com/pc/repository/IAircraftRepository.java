package com.pc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pc.model.Aircraft;
import com.pc.model.Airline;


public interface IAircraftRepository extends JpaRepository<Aircraft, Long> {
	
	List<Aircraft> findByAirline_AirlineId(Long airlineId);
	
	Aircraft findByAircraftIdAndAirline(Long aircraft, Airline airline);
	
	boolean existsByCode(String code);
}

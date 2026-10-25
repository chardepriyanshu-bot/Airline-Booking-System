package com.pc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pc.model.Airport;


public interface IAirportRepository extends JpaRepository<Airport, Long> {
	public Optional<Airport> findByIataCode(String iataCode);
	
	public List<Airport> findByCityId(Long cityId);
}

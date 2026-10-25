package com.pc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pc.model.Airline;

public interface IAirlineRepository extends JpaRepository<Airline, Long> {
	Optional<Airline> findByOwnerId(Long ownerId);
	
}

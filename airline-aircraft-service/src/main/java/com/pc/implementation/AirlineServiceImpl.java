package com.pc.implementation;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pc.client.ICityServiceClient;
import com.pc.client.IUserServiceClient;
import com.pc.dto.UserResponse;
import com.pc.enums.AirlineStatus;
import com.pc.exception.AirlineNotFoundException;
import com.pc.mapper.AirlineMapper;
import com.pc.model.Airline;
import com.pc.payload.request.AirlineRequest;
import com.pc.payload.response.AirlineResponse;
import com.pc.payload.response.CityResponse;
import com.pc.repository.IAirlineRepository;
import com.pc.service.IAirlineService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AirlineServiceImpl implements IAirlineService {
	
	private final IAirlineRepository airlineRepository;
	private final IUserServiceClient userServiceClient;
	private final ICityServiceClient cityServiceClient;
	
	@Override
	@Transactional
	public AirlineResponse createAirline(AirlineRequest request) {
		
		Airline airline=AirlineMapper.toEntity(request, request.getOwnerId());
		
		UserResponse user=userServiceClient.getUserById(request.getOwnerId());
		
		CityResponse city=cityServiceClient.getCityById(request.getHeadquaterCityId());
		
		airline.setCreatedAt(Instant.now());
		
		Airline savedAirline = airlineRepository.save(airline);
		
		AirlineResponse response=AirlineMapper.toResponse(savedAirline);
		
		response.setOwner(user);
		
		response.setHeadquaterCity(city);
		
		return response;
	}

	@Override
	@Transactional
	public AirlineResponse getAirlineById(Long airlineId) {
		
		Airline savedAirline= airlineRepository.findById(airlineId).orElseThrow(
				() -> new AirlineNotFoundException("Airline not found with id: "+airlineId));
		
		return AirlineMapper.toResponse(savedAirline);
	}

	@Override
	@Transactional
	public AirlineResponse getAirlineByOwnerId(Long ownerId) {
		
		Airline savedAirline= airlineRepository.findByOwnerId(ownerId).orElseThrow(
				() -> new AirlineNotFoundException("Airline not found with ownerId: "+ownerId));
		
		return AirlineMapper.toResponse(savedAirline);
	}

	@Override
	@Transactional
	public Page<AirlineResponse> getAllAirlines(Pageable pageable) {
		
		return airlineRepository.findAll(pageable).map(AirlineMapper::toResponse);
	}

	@Override
	@Transactional
	public AirlineResponse updateAirline(AirlineRequest request, Long ownerId) {
		
		Airline airline= airlineRepository.findByOwnerId(ownerId).orElseThrow(
				() -> new AirlineNotFoundException("Airline not found with ownerId: "+ownerId));
		
		Airline updatedAirline=AirlineMapper.toUpdate(airline, request);
		
		Airline savedAirline = airlineRepository.save(updatedAirline);
		
		return AirlineMapper.toResponse(savedAirline);
	}

	@Override
	@Transactional
	public void deleteAirline(Long airlineId, Long ownerId) {
		
		Airline savedAirline= airlineRepository.findByOwnerId(ownerId).orElseThrow(
				() -> new AirlineNotFoundException("Airline not found with ownerId: "+ownerId));
		
		airlineRepository.delete(savedAirline);
	}

	@Override
	@Transactional
	public AirlineResponse changeAirlineStatus(Long airlineId, AirlineStatus status) {
		
		Airline savedAirline= airlineRepository.findById(airlineId).orElseThrow(
				() -> new AirlineNotFoundException("Airline not found with id: "+airlineId));
		
		savedAirline.setStatus(status);
		
		Airline updatedAirline = airlineRepository.save(savedAirline);
		
		return AirlineMapper.toResponse(updatedAirline);
	}

}

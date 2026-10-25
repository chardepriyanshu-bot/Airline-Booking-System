package com.pc.implementation;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pc.exception.AircraftAlreadyExistException;
import com.pc.exception.AircraftNotFoundException;
import com.pc.exception.AirlineNotFoundException;
import com.pc.mapper.AircraftMapper;
import com.pc.model.Aircraft;
import com.pc.model.Airline;
import com.pc.payload.request.AircraftRequest;
import com.pc.payload.response.AircraftResponse;
import com.pc.repository.IAircraftRepository;
import com.pc.repository.IAirlineRepository;
import com.pc.service.IAircraftService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AircraftServiceImplementation implements IAircraftService {
	
	private final IAircraftRepository aircraftRepository;
	private final IAirlineRepository airlineRepository;
	
	@Transactional
	@Override
	public AircraftResponse createAircraft(AircraftRequest request, Long ownerId) {
		
		Airline airline = airlineRepository.findById(ownerId).orElseThrow(
				() -> new AirlineNotFoundException("Airline not found"));
		
		Aircraft aircraft= AircraftMapper.toEntity(request, airline);
		
		Aircraft savedAircraft = aircraftRepository.save(aircraft);
		
		return AircraftMapper.toResponse(savedAircraft);
	}

	@Override
	public AircraftResponse getAircraftById(Long aircraftId) {
		Aircraft savedAircraft = aircraftRepository.findById(aircraftId).orElseThrow(
				() -> new AircraftNotFoundException("Aircraft does not exist"));
		
		return AircraftMapper.toResponse(savedAircraft);
	}

	@Override
	public List<AircraftResponse>getAllAircraftsByOwner(Long ownerId){	
		return aircraftRepository.findByAirline_AirlineId(ownerId)
				.stream()
				.map(AircraftMapper::toResponse)
				.toList();
	}

	@Override
	@Transactional
	public AircraftResponse updateAircraft(Long aircraftId,AircraftRequest request, Long ownerId) {
		
		Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(
				() -> new AirlineNotFoundException("Airline doesn't exist with id: "+ownerId));
		
		Aircraft aircraft= aircraftRepository.findByAircraftIdAndAirline(aircraftId, airline);
		
		if(aircraft==null) 
			throw new AircraftNotFoundException("Aircraft does not exist with id: "+aircraftId);
		
		if(!aircraft.getCode().equals(request.getCode()) && aircraftRepository.existsByCode(aircraft.getCode()))
			throw new AircraftAlreadyExistException("Code is already exist with another aircraft");
		
		Aircraft savedAircraft = aircraftRepository.save(aircraft);
		
		Aircraft updatedAircraft= AircraftMapper.toUpdate(request, savedAircraft);
		
		return AircraftMapper.toResponse(updatedAircraft);
	}

	@Override
	public void deleteAircraftById(Long aircraftId, Long ownerId) {
		
		Airline airline = airlineRepository.findByOwnerId(ownerId).orElseThrow(
				() -> new AirlineNotFoundException("Airline doesn't exist with id: "+ownerId));
		
		Aircraft aircraft= aircraftRepository.findByAircraftIdAndAirline(aircraftId, airline);
		
		if(aircraft==null) 
			throw new AircraftNotFoundException("Aircraft does not exist with id: "+aircraftId);
		
		
		aircraftRepository.deleteById(aircraftId);
	}

}

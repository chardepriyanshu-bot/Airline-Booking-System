package com.pc.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pc.exception.AirportAlreadyExistException;
import com.pc.exception.AirportNotFoundException;
import com.pc.exception.CityNotFoundException;
import com.pc.mapper.AirportMapper;
import com.pc.model.Airport;
import com.pc.model.City;
import com.pc.payload.request.AirportRequest;
import com.pc.payload.response.AirportResponse;
import com.pc.repository.IAirportRepository;
import com.pc.repository.ICityRepository;
import com.pc.service.IAirportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements IAirportService{
	
	private final IAirportRepository airportRepository;
	private final ICityRepository cityRepository;
	
	@Override
	public AirportResponse createAirport(AirportRequest request) {
		if(airportRepository.findByIataCode(request.getIataCode()).isPresent())
			throw new AirportAlreadyExistException("Airport already exist");
		
		City city=cityRepository.findById(request.getCityId())
				.orElseThrow(() -> new CityNotFoundException("City not found"));
		
		Airport airport= AirportMapper.toEntity(request);
		
		airport.setCity(city);
		
		Airport savedAirport = airportRepository.save(airport);
		
		return AirportMapper.toResponse(savedAirport);
	}

	@Override
	public AirportResponse getAirportById(Long id) {
		Airport airport = airportRepository.findById(id)
				.orElseThrow(() -> new AirportNotFoundException("Airport not found."));
		
		return AirportMapper.toResponse(airport);
	}

	@Override
	public List<AirportResponse> getAllAirport() {
		return airportRepository.findAll().stream()
				.map(AirportMapper::toResponse).toList();
	}

	@Override
	public AirportResponse updateAirport(Long id, AirportRequest request) {
		
		Airport existingAirport = airportRepository.findById(id)
				.orElseThrow(() -> new AirportNotFoundException("Airport not found."));
		
		if(request.getIataCode()!=null && !existingAirport.getIataCode().equals(request.getIataCode())
				&& airportRepository.findByIataCode(request.getIataCode()).isPresent())
			throw new AirportAlreadyExistException("Airport already exist");
		
		Airport savedAirport = airportRepository.save(AirportMapper.updateAirport(existingAirport, request));
		return AirportMapper.toResponse(savedAirport);
	}

	@Override
	public void deleteAirport(Long id) {
		Airport airport = airportRepository.findById(id)
				.orElseThrow(() -> new AirportNotFoundException("Airport not exists with provided id."));
		
		airportRepository.deleteById(id);	
	}

	@Override
	public List<AirportResponse> getAirportByCityId(Long cityId) {
		
		return airportRepository.findByCityId(cityId).stream()
				.map(AirportMapper::toResponse).toList();
	}

}

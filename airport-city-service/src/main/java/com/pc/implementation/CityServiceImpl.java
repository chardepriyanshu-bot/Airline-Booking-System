package com.pc.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pc.exception.CityAlreadyExistException;
import com.pc.exception.CityNotFoundException;
import com.pc.mapper.CityMapper;
import com.pc.model.City;
import com.pc.payload.request.CityRequest;
import com.pc.payload.response.CityResponse;
import com.pc.repository.ICityRepository;
import com.pc.service.ICityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements ICityService {
	
	private final ICityRepository cityRepository;

	@Override
	public CityResponse createCity(CityRequest request){
		if(this.cityExists(request.getCityCode()))
			throw new CityAlreadyExistException("City with given code already exists");
		
		City city = CityMapper.getCity(request);
		
		City savedCity= cityRepository.save(city);
		
		return CityMapper.toResponse(savedCity);
	}

	@Override
	public CityResponse getCityById(Long id) {
		City city = cityRepository.findById(id)
				.orElseThrow(() -> new CityNotFoundException("city not exist"));
		return CityMapper.toResponse(city);
	}

	@Override
	public CityResponse updateCity(Long id, CityRequest cityRequest) {
		City city = cityRepository.findById(id)
				.orElseThrow(() -> new CityNotFoundException("city not exist"));
		
		if(this.cityExists(cityRequest.getCityCode())) {
			throw new CityAlreadyExistException("city with given code already exist");
		}
		
		City updatedCity=cityRepository.save(CityMapper.updateEntity(city, cityRequest));
		
		return CityMapper.toResponse(updatedCity);
	}
	
	public void deleteCity(Long id) {
		cityRepository.findById(id)
				.orElseThrow(() -> new CityNotFoundException("city not exist"));
		
		cityRepository.deleteById(id);
	}

	@Override
	public Page<CityResponse> getAllCities(Pageable pageable) {
		
		return cityRepository.findAll(pageable).map(CityMapper::toResponse);
	}

	@Override
	public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
		
		return cityRepository.searchByKeyword(keyword, pageable)
				.map(CityMapper::toResponse);
	}

	@Override
	public Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable) {
		
		return cityRepository.findByCountryCodeIgnoreCase(countryCode,pageable)
				.map(CityMapper::toResponse);
	}

	@Override
	public boolean cityExists(String cityCode) {	
		return cityRepository.existsByCityCode(cityCode);
	}

}

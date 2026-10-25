package com.pc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pc.payload.request.CityRequest;
import com.pc.payload.response.CityResponse;

public interface ICityService {
	public CityResponse createCity(CityRequest request);
	public CityResponse getCityById(Long id);
	public CityResponse updateCity(Long id, CityRequest cityRequest);
	public void deleteCity(Long id);
	public Page<CityResponse> getAllCities(Pageable pageable);
	public Page<CityResponse> searchCities(String keyword, Pageable pageable);
	public Page<CityResponse> getCitiesByCountryCode(String countryCode,Pageable pageable);
	public boolean cityExists(String cityCode);
}

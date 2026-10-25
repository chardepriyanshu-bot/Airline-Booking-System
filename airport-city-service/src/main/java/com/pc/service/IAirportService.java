package com.pc.service;

import java.util.List;

import com.pc.payload.request.AirportRequest;
import com.pc.payload.response.AirportResponse;

public interface IAirportService {
	
	public AirportResponse createAirport(AirportRequest request);

	public AirportResponse getAirportById(Long id);
	
	public List<AirportResponse> getAllAirport();
	
	public AirportResponse updateAirport(Long id, AirportRequest request);
	
	public void deleteAirport(Long id);
	
	public List<AirportResponse> getAirportByCityId(Long cityId);
}
	
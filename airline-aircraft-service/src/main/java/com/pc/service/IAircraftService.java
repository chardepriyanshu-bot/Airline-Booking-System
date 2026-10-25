package com.pc.service;

import java.util.List;

import com.pc.payload.request.AircraftRequest;
import com.pc.payload.response.AircraftResponse;

public interface IAircraftService {
	public AircraftResponse createAircraft(AircraftRequest request, Long ownerId);
	public AircraftResponse getAircraftById(Long aircraftId);
	public List<AircraftResponse> getAllAircraftsByOwner(Long ownerId);
	public AircraftResponse updateAircraft(Long aircraftId, AircraftRequest request, Long ownerId);
	public void deleteAircraftById(Long aircraftId, Long ownerId); 	
}

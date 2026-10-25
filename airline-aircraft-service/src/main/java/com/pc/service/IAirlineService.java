package com.pc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pc.enums.AirlineStatus;
import com.pc.payload.request.AirlineRequest;
import com.pc.payload.response.AirlineResponse;

public interface IAirlineService {
	public AirlineResponse createAirline(AirlineRequest request);
	public AirlineResponse getAirlineById(Long airlineId);
	public AirlineResponse getAirlineByOwnerId(Long ownerId);
	public Page<AirlineResponse> getAllAirlines(Pageable pageable);
	public AirlineResponse updateAirline(AirlineRequest request, Long ownerId);
	public void deleteAirline(Long airlineId,Long ownerId);
	public AirlineResponse changeAirlineStatus(Long airlineId, AirlineStatus status);
}

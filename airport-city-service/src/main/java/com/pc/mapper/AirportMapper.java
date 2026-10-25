package com.pc.mapper;

import com.pc.model.Airport;
import com.pc.payload.request.AirportRequest;
import com.pc.payload.response.AirportResponse;

public class AirportMapper {
	
	public static Airport toEntity(AirportRequest request) {
		if(request==null)
			return null;
			
		return Airport.builder()
				.iataCode(request.getIataCode())
				.name(request.getName())
				.address(request.getAddress())
				.timeZoneId(request.getTimeZone())
				.geoCode(request.getGeoCode())
				.build();
	}
	
	public static AirportResponse toResponse(Airport airport) {
		if(airport==null)
			return null;
		
		return AirportResponse.builder()
				.id(airport.getId())
				.name(airport.getName())
				.iataCode(airport.getIataCode())
				.address(airport.getAddress())
				.city(CityMapper.toResponse(airport.getCity()))
				.timeZone(airport.getTimeZoneId())
				.geoCode(airport.getGeoCode())
				.build();
	}
	
	public static Airport updateAirport(Airport airport, AirportRequest request) {
		if(airport==null || request==null) return null;
		
		if(request.getName()!=null) {
			airport.setName(request.getName().trim());
		}
		if(request.getIataCode()!=null) {
			airport.setIataCode(request.getIataCode().trim());
		}
		if(request.getAddress()!=null) {
			airport.setAddress(request.getAddress());
		}
		if(request.getGeoCode()!=null) {
			airport.setGeoCode(request.getGeoCode());
		}
		if(request.getTimeZone()!=null) {
			airport.setTimeZoneId(request.getTimeZone());
		}
		return airport;
	}
}

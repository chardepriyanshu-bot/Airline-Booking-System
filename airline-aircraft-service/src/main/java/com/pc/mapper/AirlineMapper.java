package com.pc.mapper;

import com.pc.dto.UserResponse;
import com.pc.emmedable.Support;
import com.pc.model.Airline;
import com.pc.payload.request.AirlineRequest;
import com.pc.payload.response.AirlineResponse;

public class AirlineMapper {
	
	public static Airline toEntity(AirlineRequest request, Long ownerId) {

	    if (request == null || ownerId == null) {
	        return null;
	    }

	    Airline airline = new Airline();

	    airline.setIataCode(request.getIataCode());
	    airline.setIcaoCode(request.getIcaoCode());
	    airline.setOwnerId(ownerId);
	    airline.setName(request.getName());
	    airline.setAlias(request.getAlias());
	    airline.setLoginUrl(request.getLoginUrl());
	    airline.setWebsite(request.getWebsite());
	    airline.setStatus(request.getStatus());
	    airline.setAlliance(request.getAlliance());
	    airline.setHeadquaterCityId(request.getHeadquaterCityId());

	    if (request.getSupportEmail() != null ||
	        request.getSupportPhoneNumber() != null ||
	        request.getSupportHour() != null) {

	        Support support = new Support();

	        support.setEmail(request.getSupportEmail());
	        support.setPhoneNumber(request.getSupportPhoneNumber());
	        support.setHour(request.getSupportHour());

	        airline.setSupport(support);
	    }

	    return airline;
	}
	
	public static AirlineResponse toResponse(Airline airline) {
		if(airline==null) return null;
		
		return AirlineResponse.builder()
		        .airlineId(airline.getAirlineId())
		        .iataCode(airline.getIataCode())
		        .icaoCode(airline.getIcaoCode())
		        .name(airline.getName())
		        .alias(airline.getAlias())
		        .loginUrl(airline.getLoginUrl())
		        .website(airline.getWebsite())
		        .status(airline.getStatus())   
		        .alliance(airline.getAlliance())
		        .ownerId(airline.getOwnerId())
		        .updateById(airline.getUpdateById())
		        .support(airline.getSupport())
		        .createdAt(airline.getCreatedAt())
		        .updateAt(airline.getUpdateAt())
		        .build();
	}
	
	public static Airline toUpdate(Airline airline, AirlineRequest request) {

		airline.setIataCode(request.getIataCode());
		airline.setIcaoCode(request.getIcaoCode());
		airline.setName(request.getName());
		airline.setAlias(request.getAlias());
		airline.setLoginUrl(request.getLoginUrl());
		airline.setWebsite(request.getWebsite());
		airline.setStatus(request.getStatus());
		airline.setAlliance(request.getAlliance());
		airline.setOwnerId(request.getOwnerId());

		if (request.getSupportEmail() != null ||
		    request.getSupportPhoneNumber() != null ||
		    request.getSupportHour() != null) {

		    Support support = new Support();

		    support.setEmail(request.getSupportEmail());
		    support.setPhoneNumber(request.getSupportPhoneNumber());
		    support.setHour(request.getSupportHour());

		    airline.setSupport(support);
		}
		
		return airline;
	}
}

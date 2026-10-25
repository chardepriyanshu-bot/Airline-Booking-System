package com.pc.mapper;

import com.pc.model.City;
import com.pc.payload.request.CityRequest;
import com.pc.payload.response.CityResponse;

public class CityMapper {
	
	public static City getCity(CityRequest request) {
		if(request==null) return null;
		
		return City.builder()
				.name(request.getName())
				.cityCode(request.getCityCode())
				.countryCode(request.getCountryCode())
				.regionCode(request.getRegionCode())
				.countryName(request.getCountryName())
				.timeZoneOfSet(request.getTimeZoneOfSet())
				.build();
	}
	
	public static CityResponse toResponse(City city) {
		if(city==null) return null;
		
		return CityResponse.builder()
				.id(city.getId())
				.name(city.getName())
				.cityCode(city.getCityCode())
				.countryCode(city.getCountryCode())
				.regionCode(city.getRegionCode())
				.countryName(city.getCountryName())
				.timeZoneOfSet(city.getTimeZoneOfSet())
				.build();
	}
	public static City updateEntity(City city, CityRequest request) {
		
		if(request.getName()!=null) {
			city.setName(request.getName().trim());
		}
		
		if(request.getCountryName()!=null) {
			city.setCountryName(request.getCountryName().toUpperCase().trim());
		}
		
		if(request.getCountryCode()!=null) {
			city.setCountryCode(request.getCountryCode().toUpperCase().trim());
		}
		
		if(request.getCityCode()!=null) {
			city.setCityCode(request.getCityCode().toUpperCase().trim());
		}
		
		if(request.getRegionCode()!=null) {
			city.setRegionCode(request.getRegionCode().toUpperCase().trim());
		}
		
		return city;
	}
}

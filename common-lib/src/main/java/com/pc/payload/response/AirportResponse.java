package com.pc.payload.response;

import java.time.ZoneId;

import com.pc.emmedable.Address;
import com.pc.emmedable.GeoCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirportResponse {
	private Long id;
	private String iataCode;
	private String name;
	private ZoneId timeZone;
	private Address address;
	private CityResponse city;
	private GeoCode geoCode;
}

package com.pc.payload.response;

import com.pc.payload.request.CityRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityResponse {
	private Long id;
	private String name;
	private String cityCode;
	private String countryName;
	private String countryCode;
	private String regionCode;
	private String timeZoneOfSet;
}

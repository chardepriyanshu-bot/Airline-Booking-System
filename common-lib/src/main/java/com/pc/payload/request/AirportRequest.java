package com.pc.payload.request;

import java.time.ZoneId;

import com.pc.emmedable.Address;
import com.pc.emmedable.GeoCode;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirportRequest {
	
	@NotBlank(message = "IATA code is manadatory")
	private String iataCode;
	
	@NotBlank(message="Name is required")
	private String name;

	private ZoneId timeZone;
	
	@Valid
	private Address address;
	
	@NotNull
	private long cityId;
	
	@Valid
	private GeoCode geoCode;
}

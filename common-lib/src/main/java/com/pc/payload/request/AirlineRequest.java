package com.pc.payload.request;

import com.pc.enums.AirlineStatus;

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
public class AirlineRequest {
	@NotBlank(message = "Iata code is a manadatory")
	private String iataCode;
	
	@NotBlank(message = "Icao code is a manadatory")
	private String icaoCode;
	
	@NotNull
	private Long ownerId;
	
	@NotBlank(message = "Name is a manadatory")
	private String name;
	
	@NotBlank(message = "Alias is a manadatory")
	private String alias;
	
	private String loginUrl;
	
	private String website;
	
	private AirlineStatus status;
	
	private String alliance;
	
	private Long headquaterCityId;
	
	private String supportEmail;
	
	private String supportPhoneNumber;
	
	private String supportHour;
}

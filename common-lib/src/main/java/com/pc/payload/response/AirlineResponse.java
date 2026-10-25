package com.pc.payload.response;

import java.time.Instant;

import com.pc.dto.UserResponse;
import com.pc.emmedable.Support;
import com.pc.enums.AirlineStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AirlineResponse {
	private Long airlineId;
	private String iataCode;
	private String icaoCode;
	private String name;
	private String alias;
	private String loginUrl;
	private String website;
	private AirlineStatus status;
	private String alliance;
	private CityResponse headquaterCity;
	private Long ownerId;
	private UserResponse owner;
	private Long updateById;
	private Support support;
	private Instant createdAt;
	private Instant updateAt;
}

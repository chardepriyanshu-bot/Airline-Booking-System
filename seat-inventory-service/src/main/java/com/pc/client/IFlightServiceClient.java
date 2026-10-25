package com.pc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pc.payload.response.FlightResponse;

@FeignClient(
		   name = "flight-service",
		    url = "${services.flight.url}"
		    )
public interface IFlightServiceClient {
	
	@GetMapping("/api/flight/{flightId}")
	public FlightResponse getFlightByResponse(@PathVariable("flightId") Long flightId);
}

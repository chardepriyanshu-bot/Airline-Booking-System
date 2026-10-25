package com.pc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pc.payload.response.FlightDetailsResponse;

@FeignClient(
    name = "flight-service",
    url = "${flight.service.url}"
)
public interface IFlightScheduleServiceClient {

    @GetMapping("/api/flight/{flightId}")
    public FlightDetailsResponse getFlightById( @PathVariable("flightId") Long flightId );
}

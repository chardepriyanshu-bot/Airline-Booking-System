
package com.pc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pc.payload.response.AircraftResponse;


@FeignClient(
    name = "airline-aircraft-service",
    url = "${services.aircraft.url}"
)
public interface IAircraftServiceClient {

    @GetMapping("/api/aircraft/{aircraftId}")
    public AircraftResponse getAircraftById(@PathVariable("aircraftId") Long aircraftId
    );
}


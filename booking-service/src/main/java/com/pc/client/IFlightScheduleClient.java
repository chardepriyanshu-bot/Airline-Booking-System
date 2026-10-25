package com.pc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pc.payload.response.FlightScheduleResponse;

@FeignClient(
    name = "flight-schedule-service",
    url = "${flight.schedule.service.url}"
)
public interface IFlightScheduleClient {

    @GetMapping("/api/flight-schedules/{scheduleId}")
    FlightScheduleResponse getScheduleById(
            @PathVariable("scheduleId") Long scheduleId);
}
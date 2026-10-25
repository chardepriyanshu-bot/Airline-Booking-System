package com.pc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pc.payload.request.FlightScheduleRequest;
import com.pc.payload.request.FlightSearchRequest;
import com.pc.payload.response.FlightScheduleResponse;
import com.pc.payload.response.FlightSearchResponse;

public interface IFlightScheduleService {

    public FlightScheduleResponse createFlightSchedule(FlightScheduleRequest request);

    public FlightScheduleResponse getFlightScheduleById(Long scheduleId);

    public List<FlightScheduleResponse> getAllFlightSchedules();

    public Page<FlightScheduleResponse> getFlightSchedules(Pageable pageable);

    public FlightScheduleResponse updateFlightSchedule(
            Long scheduleId, FlightScheduleRequest request);

    public void deleteFlightSchedule(Long scheduleId);
    
    public List<FlightSearchResponse> searchFlights(FlightSearchRequest request);

}

package com.pc.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pc.client.IFlightScheduleServiceClient;
import com.pc.exception.FlightScheduleNotFoundException;
import com.pc.mapper.FlightScheduleMapper;
import com.pc.model.FlightSchedule;
import com.pc.payload.request.FlightScheduleRequest;
import com.pc.payload.request.FlightSearchRequest;
import com.pc.payload.response.FlightDetailsResponse;
import com.pc.payload.response.FlightScheduleResponse;
import com.pc.payload.response.FlightSearchResponse;
import com.pc.repository.IFlightScheduleRepository;
import com.pc.service.IFlightScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements IFlightScheduleService {

    private final IFlightScheduleRepository flightScheduleRepository;
    private final IFlightScheduleServiceClient flightServiceClient;

    @Override
    @Transactional
    public FlightScheduleResponse createFlightSchedule(FlightScheduleRequest request) {

        if (!request.getArrivalDateTime().isAfter(request.getDepartureDateTime())) {
            throw new IllegalArgumentException(
                    "Arrival date and time must be after departure date and time.");
        }

        FlightSchedule schedule = FlightScheduleMapper.toEntity(request);

        FlightSchedule savedSchedule = flightScheduleRepository.saveAndFlush(schedule);

        return FlightScheduleMapper.toResponse(savedSchedule);
    }

    @Override
    @Transactional(readOnly = true)
    public FlightScheduleResponse getFlightScheduleById(Long scheduleId) {

        FlightSchedule schedule = flightScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new FlightScheduleNotFoundException(
                        "Flight schedule not found with ID: " + scheduleId));

        return FlightScheduleMapper.toResponse(schedule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FlightScheduleResponse> getAllFlightSchedules() {

        return flightScheduleRepository.findAll()
                .stream()
                .map(FlightScheduleMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FlightScheduleResponse> getFlightSchedules(Pageable pageable) {

        return flightScheduleRepository.findAll(pageable)
                .map(FlightScheduleMapper::toResponse);
    }

    @Override
    @Transactional
    public FlightScheduleResponse updateFlightSchedule(
            Long scheduleId, FlightScheduleRequest request) {

        FlightSchedule existingSchedule = flightScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new FlightScheduleNotFoundException(
                        "Flight schedule not found with ID: " + scheduleId));

        if (!request.getArrivalDateTime().isAfter(request.getDepartureDateTime())) {
            throw new IllegalArgumentException(
                    "Arrival date and time must be after departure date and time.");
        }

        existingSchedule.setFlightId(request.getFlightId());
        existingSchedule.setDepartureDateTime(request.getDepartureDateTime());
        existingSchedule.setArrivalDateTime(request.getArrivalDateTime());
        existingSchedule.setDepartureTerminal(request.getDepartureTerminal());
        existingSchedule.setArrivalTerminal(request.getArrivalTerminal());
        existingSchedule.setScheduleStatus(request.getScheduleStatus());
        existingSchedule.setBaseFare(request.getBaseFare());

        FlightSchedule updatedSchedule =
                flightScheduleRepository.saveAndFlush(existingSchedule);

        return FlightScheduleMapper.toResponse(updatedSchedule);
    }

    @Override
    @Transactional
    public void deleteFlightSchedule(Long scheduleId) {

        FlightSchedule schedule = flightScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new FlightScheduleNotFoundException(
                        "Flight schedule not found with ID: " + scheduleId));

        flightScheduleRepository.delete(schedule);
    }

    @Override
    public List<FlightSearchResponse> searchFlights(FlightSearchRequest request) {

        if (request.getSourceAirportId()
                .equals(request.getDestinationAirportId())) {
            throw new IllegalArgumentException(
                    "Source and destination airports cannot be the same");
        }

        LocalDateTime startTime = request.getDepartureDate().atStartOfDay();

        LocalDateTime endTime = startTime.plusDays(1);
        

        List<FlightSchedule> schedules =
        		flightScheduleRepository.searchFlights(
        			    startTime,
        			    endTime
        			);
        
        return schedules.stream()
                .map(schedule -> {
                    FlightDetailsResponse flight =
                        flightServiceClient.getFlightById(schedule.getFlightId());

                    return flight.getSourceAirportId()
                            .equals(request.getSourceAirportId())
                        && flight.getDestinationAirportId()
                            .equals(request.getDestinationAirportId())
                        ? FlightScheduleMapper.mapToResponse(schedule,flight)
                        : null;   
                })
                .filter(Objects::nonNull)
                .toList();
        }
}
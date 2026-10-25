package com.pc.payload.response;
import com.pc.enums.FlightStatus;
import lombok.Data;

@Data
public class FlightDetailsResponse {

    private Long flightId;
    private String flightName;
    private Long sourceAirportId;
    private Long destinationAirportId;
    private FlightStatus flightStatus;
}

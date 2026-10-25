package com.pc.exception;

public class FlightScheduleNotFoundException extends RuntimeException{
	
	public FlightScheduleNotFoundException(String msg) {
		
		super(msg);
	}
}

package com.pc.exception;

public class AircraftNotFoundException extends RuntimeException{
	
	public AircraftNotFoundException(String msg) {
		
		super(msg);
	}
}

package com.pc.exception;

public class AirportNotFoundException extends RuntimeException{
	
	public AirportNotFoundException(String msg) {
		super(msg);
	}
}

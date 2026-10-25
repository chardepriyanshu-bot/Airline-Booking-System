package com.pc.exception;

public class AircraftAlreadyExistException extends RuntimeException{
	
	public AircraftAlreadyExistException(String msg) {
		super(msg);
	}
}

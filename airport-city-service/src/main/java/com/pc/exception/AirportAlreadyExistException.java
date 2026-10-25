package com.pc.exception;

public class AirportAlreadyExistException extends RuntimeException{
	public AirportAlreadyExistException(String msg) {
		super(msg);
	}
}

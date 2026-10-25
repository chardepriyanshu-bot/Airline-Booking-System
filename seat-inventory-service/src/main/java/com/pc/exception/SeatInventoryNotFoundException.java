package com.pc.exception;

public class SeatInventoryNotFoundException extends RuntimeException{
	
	public SeatInventoryNotFoundException(String msg) {
		super(msg);
	}
}

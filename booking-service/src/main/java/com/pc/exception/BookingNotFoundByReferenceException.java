package com.pc.exception;

public class BookingNotFoundByReferenceException extends RuntimeException{
	
	public BookingNotFoundByReferenceException(String msg){
		super(msg);
	}
}

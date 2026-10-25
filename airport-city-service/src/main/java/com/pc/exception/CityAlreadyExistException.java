package com.pc.exception;

public class CityAlreadyExistException extends RuntimeException{
	public CityAlreadyExistException(String msg){
		super(msg);
	}
}

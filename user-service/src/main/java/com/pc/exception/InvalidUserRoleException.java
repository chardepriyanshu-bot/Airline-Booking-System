package com.pc.exception;

public class InvalidUserRoleException extends RuntimeException{
	
	public InvalidUserRoleException(String msg) {
		super(msg);
	}
}

package com.kkm.pos2.exception;

public class DatabaseConnectionException extends Exception{
	
	public DatabaseConnectionException() {
		
	}
	
	public DatabaseConnectionException(String message) {
		super(message);
	}
}

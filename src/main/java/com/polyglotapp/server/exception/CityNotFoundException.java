package com.polyglotapp.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="This place is not recognized!") 
public class CityNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public CityNotFoundException(){
		super();
	}

}

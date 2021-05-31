package com.api.restaurant.excepciones;

import java.util.Arrays;

import com.api.restaurant.dtos.ErrorDTO;

public class NotFountException extends BookingException{

	private static final long serialVersionUID = 1L;

	public NotFountException(String code, String message) {
		super(code,400,message);
	}
	
	public NotFountException(String code, String message, ErrorDTO data) {
		super(code,400,message, Arrays.asList(data));
	}

}

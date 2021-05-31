package com.api.restaurant.excepciones;

import java.util.Arrays;

import com.api.restaurant.dtos.ErrorDTO;

public class InternalServerErrorException extends BookingException{
	
	private static final long serialVersionUID = 1L;

	public InternalServerErrorException(String code, String message) {
		super(code,500,message);
	}
	
	public InternalServerErrorException(String code, String message, ErrorDTO data) {
		super(code,500,message, Arrays.asList(data));
	}

}

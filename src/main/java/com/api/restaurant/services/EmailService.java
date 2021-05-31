package com.api.restaurant.services;

import com.api.restaurant.excepciones.BookingException;

public interface EmailService {

	public String processSendEmail(String receiver,String templateCode, String currentName) throws BookingException;
}

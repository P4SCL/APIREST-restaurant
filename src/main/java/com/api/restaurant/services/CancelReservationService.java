package com.api.restaurant.services;

import com.api.restaurant.excepciones.BookingException;

public interface CancelReservationService {

	public String deleteReservation(String locator) throws BookingException;
}


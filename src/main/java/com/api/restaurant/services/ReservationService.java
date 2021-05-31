package com.api.restaurant.services;

import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.jsons.CreateReservationRest;
import com.api.restaurant.jsons.ReservationRest;

public interface ReservationService {

	ReservationRest getReservation(Long id) throws BookingException;
	
	String createReservation(CreateReservationRest createReservationRest) throws BookingException;
	
	public void updateReservation(boolean estadoReserva, String locator) throws BookingException;
}

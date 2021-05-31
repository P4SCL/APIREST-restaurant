package com.api.restaurant.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.restaurant.entities.Reservation;
import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.excepciones.InternalServerErrorException;
import com.api.restaurant.excepciones.NotFountException;
import com.api.restaurant.repositories.ReservationRepository;
import com.api.restaurant.services.CancelReservationService;
import com.api.restaurant.services.EmailService;


@Service
public class CancelReservationServiceImpl implements CancelReservationService {

	@Autowired
	private ReservationRepository reservationRespository;
	
	@Autowired
	private EmailService emailService;

	public String deleteReservation(String locator) throws BookingException {

		Reservation reservation = reservationRespository.findByLocator(locator)
				.orElseThrow(() -> new NotFountException("LOCATOR-NOT-FOUND", "LOCATOR-NOT-FOUND"));

		try {
			reservationRespository.deleteByLocator(locator);
		} catch (Exception e) {
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}
		
		//Luego de que se borre la reserva, mandamos un email al correo
		this.emailService.processSendEmail(reservation.getEmail(), "CANCEL", reservation.getName());

		return "LOCATOR_DELETED";
	}

}


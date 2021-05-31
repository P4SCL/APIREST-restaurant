package com.api.restaurant.services.impl;

import java.time.Instant;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.restaurant.entities.Reservation;
import com.api.restaurant.entities.Restaurant;
import com.api.restaurant.entities.Turn;
import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.excepciones.InternalServerErrorException;
import com.api.restaurant.excepciones.NotFountException;
import com.api.restaurant.jsons.CreateReservationRest;
import com.api.restaurant.jsons.ReservationRest;
import com.api.restaurant.repositories.ReservationRepository;
import com.api.restaurant.repositories.RestaurantRepository;
import com.api.restaurant.repositories.TurnRepository;
import com.api.restaurant.services.EmailService;
import com.api.restaurant.services.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private TurnRepository turnRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private EmailService emailService;

	private ModelMapper modelMapper = new ModelMapper();

	public String createReservation(CreateReservationRest createReservationRest) throws BookingException {

		final Restaurant restaurant = restaurantRepository.findById(createReservationRest.getRestaurantId())
				.orElseThrow(() -> new NotFountException("RESTAURANT-NOT-FOUND", "RESTAURANT-NOT-FOUND"));

		final Turn turn = turnRepository.findById(createReservationRest.getTurnId())
				.orElseThrow(() -> new NotFountException("TURN-NOT-FOUND", "TURN-NOT-FOUND"));

		String locator = generateLocator(createReservationRest.getEmail(), Instant.now().getEpochSecond());

		Reservation reservation = new Reservation();
		reservation.setLocator(locator);
		reservation.setPerson(createReservationRest.getPerson());
		reservation.setDate(createReservationRest.getDate());
		reservation.setRestaurant(restaurant);
		reservation.setTurn(turn.getName());
		reservation.setName(createReservationRest.getName());
		reservation.setEmail(createReservationRest.getEmail());

		try {
			reservationRepository.save(reservation);
		} catch (Exception e) {
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}
		// Despues de crear la reserva enviar un TEMPLATE por correo indicando si desea
		// cancelar o no la reserva.

		this.emailService.processSendEmail(createReservationRest.getEmail(), "RESERVATION",
				createReservationRest.getName());
		return locator;
	}

	public String generateLocator(String email, long timestampUnix)
			throws BookingException {
		
		String timeString = String.valueOf(timestampUnix);
		
		String codigo = email.substring(0, 2);
		
		String codigo2 = timeString.substring(timeString.length()-3, timeString.length());
		
		return codigo.concat(codigo2).concat(timeString);
	}

	// inicio del bloque

	public ReservationRest getReservation(Long id) throws BookingException {

		return modelMapper.map(getReservationEntity(id), ReservationRest.class);
	}

	public Reservation getReservationEntity(Long id) throws BookingException {

		return reservationRepository.findById(id)
				.orElseThrow(() -> new NotFountException("RESERVATION-NOT-FOUND", "RESERVATION-NOT-FOUND"));
	}

	// Cierre del bloque
	
	@Override
	public void updateReservation(boolean estadoReserva, String locator) throws BookingException {

		// Verificamos si existe el locator

		final Reservation reservation = reservationRepository.findByLocator(locator)
				.orElseThrow(() -> new NotFountException("LOCATOR-NOT-FOUND", "LOCATOR-NOT-FOUND"));
		
		reservation.setPayment(true);
		
		try {
			reservationRepository.save(reservation);
		}catch(Exception e) {
			throw new InternalServerErrorException("INTERNAL_SERVER_ERROR", "INTERNAL_SERVER_ERROR");
		}
		
	}

	

}

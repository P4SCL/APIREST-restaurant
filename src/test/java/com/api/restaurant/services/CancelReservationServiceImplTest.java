package com.api.restaurant.services;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.api.restaurant.entities.Reservation;
import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.repositories.ReservationRepository;
import com.api.restaurant.services.impl.CancelReservationServiceImpl;

public class CancelReservationServiceImplTest {

	// DATOS A MOCKEAR

	// --- LOCATOR
	private static final String LOCATOR = "RAAA 3";
	private static final String LOCATOR_MENSAJE_ESPERADO = "LOCATOR_DELETED";

	// --- RESERVATION ENTITY
	public static final Reservation RESERVATION_ENTITY = new Reservation();
	public static final Long RESERVATION_ID = 1L;
	private static final String RESERVATION_LOCATOR = "BURGUER 3C";
	private static final Long RESERVATION_PERSON = 2L;
	private static final String RESERVATION_TURN = "TURN_10";
	private static final Date DATE = new Date();
	private static final Optional<Reservation> OPTIONAL_RESERVATION = Optional.of(RESERVATION_ENTITY);

	// --- OPTIONAL EMPTY para mockear la rpta del reservationRepository.findByLocator(locator)
	private static final Optional<Reservation> OPTIONAL_RESERVATION_EMPTY = Optional.empty();

	@Mock
	private ReservationRepository reservationRepository;

	@InjectMocks
	private CancelReservationServiceImpl cancelReservationService;

	@Before
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);

		// ESTABLECIENDO LOS DATOS DE LA ENTIDAD RESERVATION
		RESERVATION_ENTITY.setId(RESERVATION_ID);
		RESERVATION_ENTITY.setLocator(RESERVATION_LOCATOR);
		RESERVATION_ENTITY.setPerson(RESERVATION_PERSON);
		RESERVATION_ENTITY.setTurn(RESERVATION_TURN);
		RESERVATION_ENTITY.setDate(DATE);
	}

	// TEST UNITARIO SIN ERRORES
	@Test
	public void deleteReservationTest() throws BookingException {
		when(reservationRepository.findByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);
		when(reservationRepository.deleteByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);

		final String LOCATOR_MENSAJE_ACTUAL = cancelReservationService.deleteReservation(LOCATOR);
		assertEquals(LOCATOR_MENSAJE_ESPERADO, LOCATOR_MENSAJE_ACTUAL);

	}

	// TEST UNITARIO CON ERRORES
	@Test(expected = BookingException.class)
	public void deleteReservationFindByLocatorErrorTest() throws BookingException {
		when(reservationRepository.findByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION_EMPTY);
		when(reservationRepository.deleteByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);
		
		final String LOCATOR_MENSAJE_ACTUAL = cancelReservationService.deleteReservation(LOCATOR);
		assertEquals(LOCATOR_MENSAJE_ESPERADO, LOCATOR_MENSAJE_ACTUAL);

		fail();
	}

	@Test(expected = BookingException.class)
	public void deleteReservationInternalServerErrorTest() throws BookingException {
		when(reservationRepository.findByLocator(LOCATOR)).thenReturn(OPTIONAL_RESERVATION);
		doThrow(Exception.class).when(reservationRepository).deleteByLocator(LOCATOR);

		final String LOCATOR_MENSAJE_ACTUAL = cancelReservationService.deleteReservation(LOCATOR);
		assertEquals(LOCATOR_MENSAJE_ESPERADO, LOCATOR_MENSAJE_ACTUAL);
		fail();
	}

}

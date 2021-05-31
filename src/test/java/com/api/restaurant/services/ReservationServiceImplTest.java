package com.api.restaurant.services;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.api.restaurant.entities.Reservation;
import com.api.restaurant.entities.Restaurant;
import com.api.restaurant.entities.Turn;
import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.jsons.CreateReservationRest;
import com.api.restaurant.repositories.ReservationRepository;
import com.api.restaurant.repositories.RestaurantRepository;
import com.api.restaurant.repositories.TurnRepository;
import com.api.restaurant.services.impl.ReservationServiceImpl;

public class ReservationServiceImplTest {

	// Datos del CREATE_RESERVATION_REST
	private static final CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();

	// Datos del CREATE_RESERVATION_REST
	private static final Long RESTAURANT_ID = 1L;
	private static final Date DATE = new Date();
	private static final Long PERSON = 5L;
	private static final Long TURN_ID = 3L;

	// Datos del Restaurant Entity
	private static final Restaurant RESTAURANT_ENTITY = new Restaurant();
	private static final String RESTAURANT_NAME = "BURGUERZ";
	private static final String RESTAURANT_DESCRIPTION = "Todo tipo de hamburguesas";
	private static final String RESTAURANT_ADDRESS = "Calle Callao 371";
	private static final String RESTAURANT_IMG = "www.image.com";
	private static final List<Turn> TURN_LIST = new ArrayList<>();

	// Datos del Turn Entity
	private static final Turn TURN_ENTITY = new Turn();
	private static final String TURN_NOMBRE = "TURNO_1";
	

	//Instancia y datos del Reservation Entity
	public static final Reservation RESERVATION_ENTITY = new Reservation();
	public static final Long RESERVATION_ID = 1L;
	private static final String RESERVATION_LOCATOR = "BURGUER 3C";
	private static final Long RESERVATION_PERSON = 2L;
	private static final String RESERVATION_TURN = "TURN_10";
	
	// Optionals para el return
	private static final Optional<Restaurant> OPTIONAL_RESTAURANT = Optional.of(RESTAURANT_ENTITY);
	private static final Optional<Restaurant> OPTIONAL_RESTAURANT_EMPTY = Optional.empty();

	private static final Optional<Turn> OPTIONAL_TURN = Optional.of(TURN_ENTITY);
	private static final Optional<Turn> OPTIONAL_TURN_EMPTY = Optional.empty();

	private static final Optional<Reservation> OPTIONAL_RESERVATION = Optional.of(RESERVATION_ENTITY);
	private static final Optional<Reservation> OPTIONAL_RESERVATION_EMPTY = Optional.empty();

	@Mock
	private RestaurantRepository restaurantRepository;

	@Mock
	private TurnRepository turnRepository;

	@Mock
	private ReservationRepository reservationRepository;

	@InjectMocks
	private ReservationServiceImpl reservationService;

	@Before
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);

		// Estableciendo los datos del restaurante, faltan las reservations y boards,
		// por eso al debugear me da null en esos campos.
		RESTAURANT_ENTITY.setName(RESTAURANT_NAME);
		RESTAURANT_ENTITY.setAddress(RESTAURANT_ADDRESS);
		RESTAURANT_ENTITY.setDescription(RESTAURANT_DESCRIPTION);
		RESTAURANT_ENTITY.setImage(RESTAURANT_IMG);
		RESTAURANT_ENTITY.setId(RESTAURANT_ID);
		RESTAURANT_ENTITY.setTurns(TURN_LIST);

		// ESTABLECIENDO LOS DATOS DE LA ENTIDAD TURNO
		TURN_ENTITY.setId(TURN_ID);
		TURN_ENTITY.setName(TURN_NOMBRE);
		TURN_ENTITY.setRestaurant(RESTAURANT_ENTITY);
		
		// ESTABLECIENDO LOS DATOS DE LA ENTIDAD RESERVATION
		RESERVATION_ENTITY.setId(RESERVATION_ID);
		RESERVATION_ENTITY.setLocator(RESERVATION_LOCATOR);
		RESERVATION_ENTITY.setPerson(RESERVATION_PERSON);
		RESERVATION_ENTITY.setTurn(RESERVATION_TURN);
		RESERVATION_ENTITY.setDate(DATE);

		CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		CREATE_RESERVATION_REST.setDate(DATE);
		CREATE_RESERVATION_REST.setPerson(PERSON);
		CREATE_RESERVATION_REST.setTurnId(TURN_ID);
	}

	//TEST DE CREATE_RESERVATION - SIN ERORRES
	@Test
	public void createReservationTest() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN);
		Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN_ENTITY.getName(), RESTAURANT_ENTITY.getId()))
				.thenReturn(OPTIONAL_RESERVATION_EMPTY);

		// Guardando la reserva con reservationRepository.save();
		Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(new Reservation());
		reservationService.createReservation(CREATE_RESERVATION_REST);
	}

	//TEST DE CREATE_RESERVATION / RestaurantFindById - CON ERORRES
	@Test(expected = BookingException.class)
	public void createReservationRestaurantFindByIdTestError() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT_EMPTY);
		reservationService.createReservation(CREATE_RESERVATION_REST);
		fail();
	}

	//TEST DE CREATE_RESERVATION / TurnFindById - CON ERORRES
	@Test(expected = BookingException.class)
	public void createReservationTurnFindByIdTestError() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN_EMPTY);

		reservationService.createReservation(CREATE_RESERVATION_REST);
		fail();
	}

	@Test(expected = BookingException.class)
	public void createReservationfindByTurnAndRestaurantIdTestError() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN);
		Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN_ENTITY.getName(), RESTAURANT_ENTITY.getId()))
		.thenReturn(OPTIONAL_RESERVATION);
		
		reservationService.createReservation(CREATE_RESERVATION_REST);
		fail();
	}
	
	@Test(expected = BookingException.class)
	public void createReservationInternalServerErrorTest() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN);
		Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN_ENTITY.getName(), RESTAURANT_ENTITY.getId()))
				.thenReturn(OPTIONAL_RESERVATION_EMPTY);

		// Invocando el error
		Mockito.doThrow(Exception.class).when(reservationRepository).save(Mockito.any(Reservation.class));
		reservationService.createReservation(CREATE_RESERVATION_REST);
		fail();
	}
}

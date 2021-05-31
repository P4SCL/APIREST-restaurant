package com.api.restaurant.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.api.restaurant.controllers.ReservationController;
import com.api.restaurant.controllers.responses.BookingResponse;
import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.jsons.CreateReservationRest;
import com.api.restaurant.services.ReservationService;

public class ReservationControllerTest {

	// Response
	private static final String SUCCESS_STATUS = "Success";
	private static final String STATUS_CODE = "200 OK";
	private static final String STATUS_MESSAGE = "Ok";
	
	//Datos del CREATE_RESERVATION_REST
	private static final CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();
	
	//Datos del CREATE_RESERVATION_REST
	private static final Long RESTAURANT_ID = 1L;
	private static final Date DATE  = new Date();
	private static final Long PERSON = 5L;
	private static final Long TURN_ID = 3L;
	
	//ThenReturn LOCATOR
	private static final String LOCATOR = "BURGUER 2"; 

	@Mock
	ReservationService reservationService;
	
	@InjectMocks
	ReservationController reservationController; 

	@Before
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);
		
		CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		CREATE_RESERVATION_REST.setDate(DATE);
		CREATE_RESERVATION_REST.setPerson(PERSON);
		CREATE_RESERVATION_REST.setTurnId(TURN_ID);
		
		Mockito.when(reservationService.createReservation(CREATE_RESERVATION_REST)).thenReturn(LOCATOR);
	}
	
	@Test
	public void createReservationTest() throws BookingException{
		BookingResponse<String> response = reservationController.createReservation(CREATE_RESERVATION_REST);
		
		assertEquals(SUCCESS_STATUS, response.getStatus());
		assertEquals(STATUS_CODE, response.getCode());
		assertEquals(STATUS_MESSAGE, response.getMessage());
		assertEquals(LOCATOR, response.getData());
	}
}

package com.api.restaurant.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.api.restaurant.controllers.CancelReservationController;
import com.api.restaurant.controllers.responses.BookingResponse;
import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.services.CancelReservationService;

public class CancelReservationControllerTest {

	//Response
	private static final String SUCCESS_STATUS = "Success";
	private static final String STATUS_CODE = "200 OK";
	private static final String STATUS_MESSAGE = "Ok";
	
	//Locator
	private static final String LOCATOR = "BURGUER 3GG";
	
	//Mensaje de que responde el service "LOCATOR_DELETED" al borrarlo.
	private static final String MENSAJE_EXITO = "LOCATOR_DELETED";
	
	@Mock
	private CancelReservationService cancelReservationService;
	
	@InjectMocks
	private CancelReservationController cancelReservationController;
	
	@Before
	public void init() throws BookingException{
		MockitoAnnotations.initMocks(this);
		
		Mockito.when(cancelReservationService.deleteReservation(LOCATOR)).thenReturn(MENSAJE_EXITO);
	}
	
	@Test
	public void deleteReservation() throws BookingException{
		final BookingResponse<String> response =  cancelReservationController.deleteReservation(LOCATOR);
		assertEquals(SUCCESS_STATUS, response.getStatus());
		assertEquals(STATUS_CODE, response.getCode());
		assertEquals(STATUS_MESSAGE, response.getMessage());
		assertEquals(MENSAJE_EXITO, response.getData());
	}
}

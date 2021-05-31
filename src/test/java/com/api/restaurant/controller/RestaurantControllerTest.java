package com.api.restaurant.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.api.restaurant.controllers.RestaurantController;
import com.api.restaurant.controllers.responses.BookingResponse;
import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.jsons.RestaurantRest;
import com.api.restaurant.jsons.TurnRest;
import com.api.restaurant.services.RestaurantService;

public class RestaurantControllerTest {
	
	private static final Long RESTAURANT_ID = 1L;
	
	//Response
	private static final String SUCCESS_STATUS = "Success";
	private static final String STATUS_CODE = "200 OK";
	private static final String STATUS_MESSAGE = "Ok";
	
	private static final RestaurantRest RESTAURANT_REST = new RestaurantRest();
	private static final List<TurnRest> TURN_LIST = new ArrayList<>();
	
	//Datos mock del RestaurantRest
	private static final String RESTAURANT_NAME = "BURGUERZ";
	private static final String RESTAURANT_DESCRIPTION = "Todo tipo de hamburguesas";
	private static final String RESTAURANT_ADDRESS = "Calle Callao 371";
	private static final String RESTAURANT_IMG = "www.image.com";
	
	//Lista de Restaurantes
	private static final List<RestaurantRest> RESTAURANTS_LIST = new ArrayList<>();
	
	@Mock
	RestaurantService restaurantService;
	
	@InjectMocks
	RestaurantController restaurantController;

	@Before
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);
		
		RESTAURANT_REST.setName(RESTAURANT_NAME);
		RESTAURANT_REST.setDescription(RESTAURANT_DESCRIPTION);
		RESTAURANT_REST.setAddress(RESTAURANT_ADDRESS);
		RESTAURANT_REST.setId(RESTAURANT_ID);
		RESTAURANT_REST.setImage(RESTAURANT_IMG);
		RESTAURANT_REST.setTurns(TURN_LIST);
		
		when(restaurantService.getRestaurantById(RESTAURANT_ID)).thenReturn(RESTAURANT_REST);
		
		
	}
	
	@Test
	public void getRestaurantByIdTest() throws BookingException{
		final BookingResponse<RestaurantRest> response = restaurantController.getRestaurantById(RESTAURANT_ID);

		assertEquals(SUCCESS_STATUS,response.getStatus());
		assertEquals(STATUS_CODE,response.getCode());
		assertEquals(STATUS_MESSAGE,response.getMessage());
		assertEquals(RESTAURANT_REST,response.getData());
		
	}
	
	@Test
	public void getRestaurantsTest() throws BookingException {
		final BookingResponse<List<RestaurantRest>> response = restaurantController.getRestaurants();

		assertEquals(SUCCESS_STATUS,response.getStatus());
		assertEquals(STATUS_CODE,response.getCode());
		assertEquals(STATUS_MESSAGE,response.getMessage());
		assertEquals(RESTAURANTS_LIST,response.getData());
		
		//Verificando la lista contenga elementos
		//assertEquals(1, response.getData().size());
	}
	
}

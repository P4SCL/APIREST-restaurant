package com.api.restaurant.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.api.restaurant.entities.Board;
import com.api.restaurant.entities.Reservation;
import com.api.restaurant.entities.Restaurant;
import com.api.restaurant.entities.Turn;
import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.jsons.RestaurantRest;
import com.api.restaurant.repositories.RestaurantRepository;
import com.api.restaurant.services.impl.RestaurantServiceImpl;

public class RestaurantServiceImplTest {
	
	
	
	//---- Datos MOCK del Restaurant Entity
	private static final Restaurant RESTAURANT_ENTITY = new Restaurant();
	private static final Long RESTAURANT_ID = 1L;
	private static final String RESTAURANT_NAME = "BURGUERZ";
	private static final String RESTAURANT_DESCRIPTION = "Todo tipo de hamburguesas";
	private static final String RESTAURANT_ADDRESS = "Calle Callao 371";
	private static final String RESTAURANT_IMG = "www.image.com";
	
	private static final List<Turn> TURN_LIST = new ArrayList<>();
	private static final List<Reservation> RESERVATION_LIST = new ArrayList<>();
	private static final List<Board> BOARD_LIST = new ArrayList<>();
	
	@Mock
	RestaurantRepository restaurantRepository;
	
	@InjectMocks
	RestaurantServiceImpl restaurantService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		RESTAURANT_ENTITY.setName(RESTAURANT_NAME);
		RESTAURANT_ENTITY.setAddress(RESTAURANT_ADDRESS);
		RESTAURANT_ENTITY.setDescription(RESTAURANT_DESCRIPTION);
		RESTAURANT_ENTITY.setImage(RESTAURANT_IMG);
		RESTAURANT_ENTITY.setId(RESTAURANT_ID);
		RESTAURANT_ENTITY.setReservations(RESERVATION_LIST);
		RESTAURANT_ENTITY.setTurns(TURN_LIST);
		RESTAURANT_ENTITY.setBoards(BOARD_LIST);
	}
	
	@Test
	public void getRestaurantByIdTest() throws BookingException {
		when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT_ENTITY));
		restaurantService.getRestaurantById(RESTAURANT_ID);
	}
	
	@Test(expected = BookingException.class)
	public void getRestaurantByIdTestError() throws BookingException {
		when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.empty());
		restaurantService.getRestaurantById(RESTAURANT_ID);
		fail();
	}
	
	@Test
	public void getRestaurantsTest() throws BookingException {
		final Restaurant restaurant = new Restaurant();
		final List<Restaurant> restaurants = new ArrayList<>();
		restaurants.add(restaurant);
		
		when(restaurantRepository.findRestaurants()).thenReturn(restaurants);
		
		List<RestaurantRest> restaurants_rest = restaurantService.getRestaurants();
		
		assertEquals(1, restaurants_rest.size());
		assertFalse(restaurants_rest.isEmpty());
		
	}
}

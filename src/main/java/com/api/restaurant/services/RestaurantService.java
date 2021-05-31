package com.api.restaurant.services;

import java.util.List;

import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.jsons.RestaurantRest;

public interface RestaurantService {

	RestaurantRest getRestaurantById(Long id) throws BookingException;
	
	public List<RestaurantRest> getRestaurants() throws BookingException;
	
	
}

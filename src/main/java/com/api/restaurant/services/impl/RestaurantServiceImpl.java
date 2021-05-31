package com.api.restaurant.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.restaurant.entities.Restaurant;
import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.excepciones.NotFountException;
import com.api.restaurant.jsons.RestaurantRest;
import com.api.restaurant.repositories.RestaurantRepository;
import com.api.restaurant.services.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	// Creando instancia model mapper
	private ModelMapper modelMapper = new ModelMapper();

	public RestaurantRest getRestaurantById(Long restaurantId) throws BookingException {

		return modelMapper.map(getRestaurantEntity(restaurantId), RestaurantRest.class);
	}

	public List<RestaurantRest> getRestaurants() throws BookingException {

		final List<Restaurant> restaurantEntity = restaurantRepository.findRestaurants();
		return restaurantEntity.stream().map(elemento -> modelMapper.map(elemento, RestaurantRest.class))
				.collect(Collectors.toList());
	}

	private Restaurant getRestaurantEntity(Long restaurantId) throws BookingException {
		return restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new NotFountException("NOT-404-1", "RESTAURANT NOT FOUND"));
	}

}

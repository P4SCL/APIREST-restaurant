package com.api.restaurant.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.restaurant.controllers.responses.BookingResponse;
import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.jsons.RestaurantRest;
import com.api.restaurant.services.RestaurantService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/booking-restaurant" + "/v1")
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "restaurant/{restaurantId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public BookingResponse<RestaurantRest> getRestaurantById(@PathVariable Long restaurantId) throws BookingException {
		return new BookingResponse<>("Success", String.valueOf(HttpStatus.OK), "Ok",
				restaurantService.getRestaurantById(restaurantId));
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value="restaurants", method =RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public BookingResponse<List<RestaurantRest>> getRestaurants() throws BookingException {
		return new BookingResponse<>("Success", String.valueOf(HttpStatus.OK), "Ok",
				restaurantService.getRestaurants());
	}
	

}

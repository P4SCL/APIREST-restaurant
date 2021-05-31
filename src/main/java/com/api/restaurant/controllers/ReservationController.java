package com.api.restaurant.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.restaurant.controllers.responses.BookingResponse;
import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.jsons.CreateReservationRest;
import com.api.restaurant.services.ReservationService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/booking-restaurant" + "/v1")
public class ReservationController {

	@Autowired
	ReservationService reservationService;
	
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping(value = "reservation", produces = MediaType.APPLICATION_JSON_VALUE)
	public BookingResponse<String> createReservation(@RequestBody @Valid CreateReservationRest createReservationRest) throws BookingException{
		return new BookingResponse<>
				("Success",String.valueOf(HttpStatus.OK),"Ok",reservationService.createReservation(createReservationRest));
	}
}

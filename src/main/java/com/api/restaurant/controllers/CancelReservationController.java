package com.api.restaurant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.restaurant.controllers.responses.BookingResponse;
import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.services.CancelReservationService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/booking-restaurant" + "/v1")
public class CancelReservationController {

	@Autowired
	CancelReservationService cancelReservationService;
	
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value="/deleteReservation",method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BookingResponse<String> deleteReservation(@RequestParam String locator) throws BookingException{
		
		return new BookingResponse<>("Success",String.valueOf(HttpStatus.OK),"Ok",cancelReservationService.deleteReservation(locator));
	}
}

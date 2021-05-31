package com.api.restaurant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.jsons.PaymentConfirmRest;
import com.api.restaurant.jsons.PaymentIntentRest;
import com.api.restaurant.services.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/booking-restaurant" + "/v1")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	

	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping(value = "/paymentIntent", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> paymentIntent(@RequestBody PaymentIntentRest paymentIntentRest) throws StripeException{
		PaymentIntent paymentIntent = paymentService.paymentIntent(paymentIntentRest);
		
		String paymentString = paymentIntent.toJson();
		return new ResponseEntity<String>(paymentString, HttpStatus.OK);
	}
	
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping(value = "/confirm", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> paymentConfirm(@RequestBody PaymentConfirmRest paymentConfirmRest) throws StripeException, BookingException{
		PaymentIntent paymentIntent = paymentService.paymentConfirm(paymentConfirmRest);
		
		String paymentString = paymentIntent.toJson();
		return new ResponseEntity<String>(paymentString, HttpStatus.OK);
	}
	
	
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping(value = "/cancel"+"{paymentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> paymentCancel(@PathVariable("paymentId") String paymentId) throws StripeException{
		PaymentIntent paymentIntent = paymentService.paymentCancel(paymentId);
		
		String paymentString = paymentIntent.toJson();
		return new ResponseEntity<String>(paymentString, HttpStatus.OK);
	}
	
}

package com.api.restaurant.services;

import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.jsons.PaymentConfirmRest;
import com.api.restaurant.jsons.PaymentIntentRest;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface PaymentService {

	public PaymentIntent paymentIntent(PaymentIntentRest paymentIntentRest) throws StripeException;
	
	public PaymentIntent paymentConfirm(PaymentConfirmRest paymentConfirmRest) throws StripeException,BookingException;
	
	public PaymentIntent paymentCancel(String paymentId) throws StripeException;
}

package com.api.restaurant.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.api.restaurant.excepciones.BookingException;
import com.api.restaurant.jsons.PaymentConfirmRest;
import com.api.restaurant.jsons.PaymentIntentRest;
import com.api.restaurant.services.EmailService;
import com.api.restaurant.services.PaymentService;
import com.api.restaurant.services.ReservationService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ReservationService reservationService;
	
	@Value("${stripe.key.secret}")
	String secretKey;

	@Override
	public PaymentIntent paymentIntent(PaymentIntentRest paymentIntentRest) throws StripeException {

		Stripe.apiKey = secretKey;
		Map<String, Object> params = new HashMap<>();

		params.put("amount", paymentIntentRest.getPrice());
		params.put("currency", "PEN");
		params.put("description", paymentIntentRest.getDescription());	
		List<Object> paymentMethodTypes = new ArrayList<>();

		paymentMethodTypes.add("card");
		params.put("payment_method_types", paymentMethodTypes);

		return PaymentIntent.create(params);
	}

	@Override
	public PaymentIntent paymentConfirm(PaymentConfirmRest paymentConfirmRest) throws StripeException, BookingException {
		Stripe.apiKey = secretKey;

		PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentConfirmRest.getPaymentId());

		Map<String, Object> params = new HashMap<>();
		params.put("payment_method", "pm_card_visa");
		paymentIntent.confirm(params);
		
		//Una vez que se haya confirmado el pago enviar un correo y actualizar el campo de la tabla
		
		reservationService.updateReservation(true,paymentConfirmRest.getLocator());
		
		emailService.processSendEmail(paymentConfirmRest.getEmail(), "PAYMENT", paymentConfirmRest.getNombre());
		
		return paymentIntent;
	}

	@Override
	public PaymentIntent paymentCancel(String paymentId) throws StripeException {
		Stripe.apiKey = secretKey;

		PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentId);

		paymentIntent.cancel();

		return paymentIntent;
	}

}

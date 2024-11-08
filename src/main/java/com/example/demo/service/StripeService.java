package com.example.demo.service;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.exception.StripeException;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    public PaymentIntent createPaymentIntent(long amount) throws StripeException {
        // Set your secret key from your Stripe account
        Stripe.apiKey = "sk_test_51QI5ZoLl7bC3KPjgT9CwwAMf6lEkX1avRNSq3UK0ItzWeHOrOVbrZBG4NWoRIBdAz3KTzbH1MTrnG6lqZ1scZkSJ00awoaoGP1";

        // Create the payment intent with the amount and currency
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency("usd")
                .build();

        return PaymentIntent.create(params);
    }
}

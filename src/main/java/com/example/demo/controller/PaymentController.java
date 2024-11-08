package com.example.demo.controller;

import com.example.demo.service.StripeService;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private StripeService stripeService;

    /**
     * This endpoint creates a payment intent using the amount provided.
     *
     * @param //amount the amount for the payment.
     * @return ResponseEntity containing the client secret or error message.
     */
    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent(@RequestBody AmountRequest amountRequest) {
        try {
            PaymentIntent paymentIntent = stripeService.createPaymentIntent(amountRequest.getAmount());
            return ResponseEntity.ok(paymentIntent.getClientSecret());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Payment Intent creation failed: " + e.getMessage());
        }
    }

    public static class AmountRequest {
        private long amount;

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }
    }

}

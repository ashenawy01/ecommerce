package com.sigma.ecommerce.controller;

import com.sigma.ecommerce.dto.PaymentInfo;
import com.sigma.ecommerce.dto.Purchase;
import com.sigma.ecommerce.dto.PurchaseResponse;
import com.sigma.ecommerce.service.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
@AllArgsConstructor
public class CheckoutController {
    private CheckoutService checkoutService;

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder (@RequestBody Purchase purchase) {
        return checkoutService.placeOrder(purchase);
    }

    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
        PaymentIntent paymentIntent = checkoutService.createPaymentIntent(paymentInfo);

        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

}

package com.sigma.ecommerce.service;

import com.sigma.ecommerce.dto.PaymentInfo;
import com.sigma.ecommerce.dto.Purchase;
import com.sigma.ecommerce.dto.PurchaseResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

public interface CheckoutService {
    PurchaseResponse placeOrder(Purchase purchase);
    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;
}

package com.sigma.ecommerce.service;

import com.sigma.ecommerce.dto.PaymentInfo;
import com.sigma.ecommerce.dto.Purchase;
import com.sigma.ecommerce.dto.PurchaseResponse;
import com.sigma.ecommerce.entity.Address;
import com.sigma.ecommerce.entity.Customer;
import com.sigma.ecommerce.entity.Order;
import com.sigma.ecommerce.entity.OrderItem;
import com.sigma.ecommerce.repository.AddressRepository;
import com.sigma.ecommerce.repository.CustomerRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository, AddressRepository addressRepository, @Value("${stripe.key.secret}") String secretKey) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;

        // initialize API with secret key
        Stripe.apiKey = secretKey;
    }

    @Override
    public PurchaseResponse placeOrder(Purchase purchase) {

        // Retrieve the order from DTO
        Order order = purchase.getOrder();

        // Generate Tracking number
        String orderTrackingNumber = UUID.randomUUID().toString();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // Retrieve Order items
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);

        // Save billing address
        Address billingAddress = purchase.getBillingAddress();
        addressRepository.save(billingAddress);

        // Save shipping address
        Address shippingAddress = purchase.getShippingAddress();
        addressRepository.save(shippingAddress);

        // Set Addresses
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // Retrieve Customer
        Customer customer = purchase.getCustomer();
        Customer customerDB =  customerRepository.findByEmail(customer.getEmail());

        if (customerDB != null) {
            customer = customerDB;
        }
        customer.add(order);


        // Save / Update customer
        customerRepository.save(customer);

        //Return a response with a tracking number
        return new PurchaseResponse(orderTrackingNumber);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        List<String> paymentMethodType = new ArrayList<>();
        paymentMethodType.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_type", paymentMethodType);

        return PaymentIntent.create(params);
    }
}

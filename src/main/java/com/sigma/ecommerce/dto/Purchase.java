package com.sigma.ecommerce.dto;

import com.sigma.ecommerce.entity.Address;
import com.sigma.ecommerce.entity.Customer;
import com.sigma.ecommerce.entity.Order;
import com.sigma.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}

package com.sigma.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PurchaseResponse {
    private final String orderTrackingNumber;
}

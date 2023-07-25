package com.sigma.ecommerce.controller;

import com.sigma.ecommerce.dto.Purchase;
import com.sigma.ecommerce.dto.PurchaseResponse;
import com.sigma.ecommerce.service.CheckoutService;
import lombok.AllArgsConstructor;
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

}

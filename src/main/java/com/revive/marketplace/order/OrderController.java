package com.revive.marketplace.order;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api-endpoint}/orders")
public class OrderController {
    private OrderService service;

    
    
}

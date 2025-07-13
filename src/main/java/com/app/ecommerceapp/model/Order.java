package com.app.ecommerceapp.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Order {
    private String id;
    private String customerId;
    private Instant orderDate;
    private BigDecimal totalPrice;
}

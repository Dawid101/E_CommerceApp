package com.app.ecommerceapp.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private String id;
    //nie wiem czy jest sens dodwać orderNumber skoro id będę miał unikalne i chyba będe mógł sobie to używać jako order nr
    private String orderNumber;
    private String customerId;
    private LocalDateTime orderDate;
    private double totalPrice;
}

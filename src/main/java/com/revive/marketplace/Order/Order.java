package com.revive.marketplace.Order;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;

@Entity
public class Order {
Long id;
LocalDateTime dateOrder;
//User user;
//Product product;
String status;
Double totalPrice;
String address;

}

package com.revive.marketplace.Order;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.revive.marketplace.product.ProductModel;
import jakarta.persistence.*;

@Entity
public class Order {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(name = "date_order", nullable = false, length = 50)
LocalDateTime dateOrder;


//@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_user")
//@JsonBackReference
//User user;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "product_id", nullable = false)
@JsonBackReference
private ProductModel product;

@Enumerated(EnumType.STRING)
@Column(name = "status",nullable = false)
OrderStatus status;

@Column(name = "total_price",nullable = false)
Double totalPrice;

@Column(name= "adress", nullable = false)
String address;

public Order(Long id, LocalDateTime dateOrder, ProductModel product, OrderStatus status, Double totalPrice,
        String address) {
    this.id = id;
    this.dateOrder = dateOrder;
    this.product = product;
    this.status = status;
    this.totalPrice = totalPrice;
    this.address = address;
}

public Long getId() {
    return id;
}

public LocalDateTime getDateOrder() {
    return dateOrder;
}

public ProductModel getProduct() {
    return product;
}

public OrderStatus getStatus() {
    return status;
}

public Double getTotalPrice() {
    return totalPrice;
}

public String getAddress() {
    return address;
}

}

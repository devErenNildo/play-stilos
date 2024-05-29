package com.playstilos.domain.order;

import com.playstilos.domain.user.User;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private String id;
    @DBRef
    private String idCustomer;
    private List<OrderItem> products;
    private LocalDate orderDate;
    private String status;
    private double totalAmount;

    public Order(){

    }

    private double calculateTotalAmount(){
        return products.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}

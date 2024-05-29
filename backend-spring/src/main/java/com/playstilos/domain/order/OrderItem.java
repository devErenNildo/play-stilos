package com.playstilos.domain.order;

import com.playstilos.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItem {
    private Product product;
    private int quantity;
    private double price;
}

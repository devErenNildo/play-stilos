package com.playstilos.domain.cart;

import com.playstilos.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItem {
    @DBRef
    private Product cartProduct;
    private Integer cartItemQuantity;
    private double totalPrice;
}

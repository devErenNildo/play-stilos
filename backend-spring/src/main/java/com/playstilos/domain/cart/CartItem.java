package com.playstilos.domain.cart;

import com.playstilos.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItem {
    private Product cartProduct;
    private Integer cartItemQuantity;
}

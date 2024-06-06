package com.playstilos.domain.cart;

import com.playstilos.domain.user.User;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Document(collection = "cart")
public class Cart {
    @Id
    private String id;

    private List<CartItem> items;

    private double cartTotal;

    @DBRef
    private User cartOwner;

    public Cart(User cartOwner){
        this.cartOwner = cartOwner;
        this.items = new ArrayList<>();
    }
}



package com.playstilos.repositories;

import com.playstilos.domain.cart.Cart;
import com.playstilos.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {

    @Query("{ 'cartOwner.id' : ?0 }")
    Cart findByUserId(String idUser);
}

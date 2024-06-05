package com.playstilos.controllers;

import com.playstilos.domain.cart.Cart;
import com.playstilos.domain.cart.CartDTO;
import com.playstilos.domain.user.User;
import com.playstilos.services.AuthorizationService;
import com.playstilos.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthorizationService userService;

//    Rota para exibir carrinho
    @GetMapping
    public ResponseEntity<?> getCart(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.loadUserEmail(authentication.getName());
        CartDTO cart = cartService.getCart(user);
        return ResponseEntity.ok(cart);
    }

//    Rota para adiconar produto ao carrinho
    @PostMapping("/add/{idProduct}")
    public ResponseEntity<?> addProductToCart(@PathVariable String idProduct, @RequestParam int quantity){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.loadUserEmail(authentication.getName());
        Cart cart = cartService.addItem(user, idProduct, quantity);
        return ResponseEntity.ok(cart);
    }

}

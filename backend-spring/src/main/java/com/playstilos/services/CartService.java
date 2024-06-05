package com.playstilos.services;

import com.playstilos.domain.cart.Cart;
import com.playstilos.domain.cart.CartDTO;
import com.playstilos.domain.cart.CartItem;
import com.playstilos.domain.cart.CartItemDTO;
import com.playstilos.domain.product.Product;
import com.playstilos.domain.user.User;
import com.playstilos.repositories.CartRepository;
import com.playstilos.repositories.ProductRepository;
import com.playstilos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

//    converte um carrinho em um carrinhoDTO
    private CartDTO convertCartToCartDTO(Cart cart){
        List<CartItemDTO> itens = cart.getItems().stream()
                .map(item -> new CartItemDTO(
                        item.getCartProduct().getName(),
                        item.getCartProduct().getPrice(),
                        item.getCartItemQuantity(),
                        item.getCartProduct().getImage())
                ).collect(Collectors.toList());

        return new CartDTO(itens, cart.getCartTotal());
    }

//    verifica se o usuário ja possui um carrinho, se não tiver ele cria um.
    private Cart checkCart(User user){
        Cart cart = cartRepository.findByUserId(user.getId());
        if(cart == null){
            cart = new Cart(user);
            cartRepository.save(cart);
        }
        return cart;
    }

    public CartDTO getCart(User user){
        Cart cart = checkCart(user);

        return convertCartToCartDTO(cart);
    }

    public Cart addItem(User user, String productId, int quantity){
        Cart cart = checkCart(user);
        Product product = productRepository.findById(productId).orElseThrow();
        CartItem cartItem = new CartItem(product, quantity);
        cart.getItems().add(cartItem);
        return cartRepository.save(cart);
    }


}

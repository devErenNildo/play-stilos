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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    //    verifica se o usuário ja possui um carrinho, se não tiver ele cria um.
    private Cart checkCart(User user){
        Cart cart = cartRepository.findByUserId(user.getId());
        if(cart == null){
            cart = new Cart(user);
            cartRepository.save(cart);
        }
        return cart;
    }

//    somar o valor total do carrinho
    private double getCartPrice(Cart items){
        return items.getItems().stream()
                .mapToDouble(item -> item.getTotalPrice())
                .sum();
    }


//    converte um carrinho em um carrinhoDTO
    private CartDTO convertCartToCartDTO(Cart cart){
        List<CartItemDTO> itens = cart.getItems().stream()
                .map(item -> new CartItemDTO(
                        item.getCartProduct().getName(),
                        item.getCartProduct().getPrice(),
                        item.getCartItemQuantity(),
                        item.getCartProduct().getImage(),
                        item.getTotalPrice())
                ).collect(Collectors.toList());

        return new CartDTO(itens, cart.getCartTotal());
    }

    public CartDTO getCart(User user){
        Cart cart = checkCart(user);

        return convertCartToCartDTO(cart);
    }

//    adicionar um item no carrinho
    public Cart addItem(User user, String productId, int quantity){
        Cart cart = checkCart(user);

        Optional<CartItem> existingCartItem = cart.getItems().stream()
                .filter(item -> item.getCartProduct().getId().equals(productId))
                .findFirst();

        if (existingCartItem.isPresent()){
            CartItem cartItem = existingCartItem.get();
            int newQuantity = cartItem.getCartItemQuantity() + quantity;
            cartItem.setCartItemQuantity(newQuantity);
            cartItem.setTotalPrice(cartItem.getCartProduct().getPrice() * newQuantity);

        } else {
            Product product = productRepository.findById(productId).orElseThrow();
            CartItem newCartItem = new CartItem(product, quantity, product.getPrice() * quantity);
            cart.getItems().add(newCartItem);
        }
        cart.setCartTotal(getCartPrice(cart));

        return cartRepository.save(cart);
    }


}

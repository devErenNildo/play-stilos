package com.playstilos.domain.cart;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record CartDTO(List<CartItemDTO> items, double total){

}
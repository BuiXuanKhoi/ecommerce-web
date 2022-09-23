package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.request.CartItemRequestDTO;
import com.example.ecommerce_web.model.dto.respond.CartItemRespondDTO;
import com.example.ecommerce_web.model.entities.CartItem;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartItemService {
    CartItem addToCart(CartItemRequestDTO cartItemRequestDTO);
    List<CartItem> getListCartItem();
    void deleteCartItem(int cartItemId);
}

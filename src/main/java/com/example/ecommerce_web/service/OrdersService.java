package com.example.ecommerce_web.service;

import com.example.ecommerce_web.model.dto.respond.OrderRespondDTO;
import com.example.ecommerce_web.model.entities.Orders;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrdersService {
    Orders createOrder();
    List<Orders> getListOrder();
    Orders updateOrderState(int orderId);
}
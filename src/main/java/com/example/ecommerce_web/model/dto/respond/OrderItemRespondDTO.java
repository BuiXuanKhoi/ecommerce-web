package com.example.ecommerce_web.model.dto.respond;

import com.example.ecommerce_web.model.entities.Books;
import com.example.ecommerce_web.model.entities.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRespondDTO {

    private int orderItemId;
    private float price;
    private int quantity;
    private String bookName;
    private String imageLink;

    public OrderItemRespondDTO(OrderItems orderItems){
        setOrderItemId(orderItems.getOrderItemsID());
        setPrice(orderItems.getPrice());
        setQuantity(orderItems.getQuantity());
        Books books = orderItems.getBooks();
        setBookName(books.getBookName());
        setImageLink(books.getImageLink());
    }
}

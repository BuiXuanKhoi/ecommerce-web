package com.example.ecommerce_web.model.dto.respond;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRespondDTO {

    private int userId;
    private String userName;
    private String role;
    private String token;
    private String tokenType;

}
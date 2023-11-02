package com.example.sep2022javaspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class SignInRequest {
    private String username;
    private String password;
}

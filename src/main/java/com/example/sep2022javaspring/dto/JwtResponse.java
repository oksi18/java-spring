package com.example.sep2022javaspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class JwtResponse {
    private final String accessToken;
    private final String refreshToken;

}

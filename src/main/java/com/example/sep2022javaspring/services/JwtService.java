package com.example.sep2022javaspring.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    boolean isTokenExpired(String token);

    String extractUsername(String token);

    String generatedToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);
}

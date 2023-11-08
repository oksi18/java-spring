package com.example.sep2022javaspring.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService{

    @Value("${jwt.signedKey}")
    private String signedKey;
    private Key key;

    @PostConstruct
    public void setUpKey() {
        key = Keys.hmacShaKeyFor(signedKey.getBytes());
    }

    @Override
    public boolean isTokenExpired(String token) {
        return resolveClaim(token, Claims::getExpiration).before(new Date());
    }

    @Override
    public String extractUsername(String token) {
        return resolveClaim(token, Claims::getSubject);
    }

    @Override
    public String generatedToken(UserDetails userDetails) {
        String roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" , "));
        return generateToken(Map.of("roles", roles), userDetails, Duration.ofSeconds(30));
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(Map.of("type", "refresh"), userDetails, Duration.ofDays(2));
    }

    private String generateToken(Map<String, String> claims, UserDetails userDetails, Duration duration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date (System.currentTimeMillis() + duration.toMillis()))
                .compact();
    }

    private <T> T resolveClaim(String token, Function<Claims, T> resolver) { //метод утиліта, щоб було зручно витягувати
        Claims claims = extractClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractClaims (String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

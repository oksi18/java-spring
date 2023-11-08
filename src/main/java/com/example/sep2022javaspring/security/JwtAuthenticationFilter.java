package com.example.sep2022javaspring.security;

import com.example.sep2022javaspring.dto.ErrorDto;
import com.example.sep2022javaspring.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class  JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (!StringUtils.startsWithIgnoreCase(authorization, AUTHORIZATION_HEADER_PREFIX)){
            filterChain.doFilter(request, response );
            return;
        }

        String token = authorization.substring(AUTHORIZATION_HEADER_PREFIX.length());

        try {
        if (jwtService.isTokenExpired(token)) {
            filterChain.doFilter(request, response );
            return;
        }

        String username = jwtService.extractUsername(token);
        SecurityContext securityContext = SecurityContextHolder.getContext();

        if (StringUtils.hasText(username) && securityContext.getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(userDetails.getPassword(), userDetails.getUsername(), userDetails.getAuthorities()  );
            securityContext.setAuthentication(authentication);
        }
        }catch(JwtException e) {
            ErrorDto errorDto = ErrorDto.builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .messages(List.of(e.getMessage()))
                    .build();

            response.setStatus(errorDto.getStatusCode());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(errorDto));
            return;
        }

        filterChain.doFilter(request, response );
    }
}

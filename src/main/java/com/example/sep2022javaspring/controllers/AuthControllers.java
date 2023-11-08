package com.example.sep2022javaspring.controllers;


import com.example.sep2022javaspring.dto.JwtResponse;
import com.example.sep2022javaspring.dto.RefreshRequest;
import com.example.sep2022javaspring.dto.SignInRequest;
import com.example.sep2022javaspring.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthControllers {
    
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtService jwtService;

    @PostMapping("/api/auth/signin")
    public ResponseEntity<JwtResponse> signIn(@RequestBody SignInRequest signInRequest){
        Authentication  authentication = UsernamePasswordAuthenticationToken
                .unauthenticated(signInRequest.getUsername(), signInRequest.getPassword());
        authenticationManager.authenticate(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(signInRequest.getUsername());
        String accesstoken = jwtService.generatedToken(userDetails);
        String refreshtoken = jwtService.generateRefreshToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(accesstoken, refreshtoken));
    }

    @PostMapping("/api/auth/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody RefreshRequest refreshRequest){
        String refreshToken = refreshRequest.getRefreshToken();

        if (jwtService.isTokenExpired(refreshToken)){
            return ResponseEntity.badRequest().build();
        }

        String username = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String accessToken = jwtService.generatedToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));

    }
}

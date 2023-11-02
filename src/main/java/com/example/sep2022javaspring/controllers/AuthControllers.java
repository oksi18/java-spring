package com.example.sep2022javaspring.controllers;


import com.example.sep2022javaspring.dto.JwtResponse;
import com.example.sep2022javaspring.dto.SignInRequest;
import com.example.sep2022javaspring.services.JwtService;
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
public class AuthControllers {
    
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtService jwtService;

    @PostMapping("/api/auth/signin")
    public ResponseEntity<JwtResponse> signIn(@RequestBody SignInRequest signInRequest){
        Authentication  authentication = UsernamePasswordAuthenticationToken
                .unauthenticated(signInRequest.getPassword(), signInRequest.getUsername() );
         authenticationManager.authenticate(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(signInRequest.getUsername());
        String token = jwtService.generatedToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}

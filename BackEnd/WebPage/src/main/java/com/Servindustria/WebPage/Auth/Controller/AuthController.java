package com.Servindustria.WebPage.Auth.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Servindustria.WebPage.Auth.DTO.AuthResponse;
import com.Servindustria.WebPage.Auth.DTO.LoginRequest;
import com.Servindustria.WebPage.Auth.DTO.RegisterRequest;
import com.Servindustria.WebPage.Auth.Service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value="login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){

        return ResponseEntity.ok(authService.login(request));
    }

    
    @PostMapping(value="register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
}

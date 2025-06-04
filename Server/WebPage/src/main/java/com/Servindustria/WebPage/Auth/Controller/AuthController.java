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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value="login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return authService.login(request);

    }

    @GetMapping(value="isAuthenticated")
    public ResponseEntity<AuthResponse> isAuthenticated(@CookieValue(value = "token") String token){
        return authService.isAuthenticated(token);
    }

    @PostMapping(value="logout")
    public ResponseEntity<AuthResponse> logout(@CookieValue(value = "token") String token){
        return authService.logout(token);
    }


    @PostMapping(value="register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return authService.register(request);
    }
}

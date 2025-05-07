package com.Servindustria.WebPage.Auth.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Servindustria.WebPage.Auth.DTO.AuthResponse;
import com.Servindustria.WebPage.Auth.DTO.LoginRequest;
import com.Servindustria.WebPage.Auth.DTO.RegisterRequest;
import com.Servindustria.WebPage.JWT.JwtService;
import com.Servindustria.WebPage.User.Model.Role;
import com.Servindustria.WebPage.User.Model.UserAccount;
import com.Servindustria.WebPage.User.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse register(RegisterRequest request) {
        UserAccount user = UserAccount.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getName())
            .last_name(request.getLast_name())
            .telephone(request.getTelephone())
            .doc(request.getDoc())
            .birthDay(request.getBirthDay())
            .role(Role.CLIENT)
            .build();
        userRepository.save(user);
        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
        }


}

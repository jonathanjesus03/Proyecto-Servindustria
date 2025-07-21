package com.Servindustria.WebPage.Auth.Service;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Servindustria.WebPage.Auth.DTO.AuthResponse;
import com.Servindustria.WebPage.Auth.DTO.LoginRequest;
import com.Servindustria.WebPage.Auth.DTO.RegisterRequest;
import com.Servindustria.WebPage.JWT.JwtService;
import com.Servindustria.WebPage.Modules.ClientAccount.Model.Role;
import com.Servindustria.WebPage.Modules.Client.Model.Client;
import com.Servindustria.WebPage.Modules.Client.Model.CompanyClient;
import com.Servindustria.WebPage.Modules.Client.Model.NaturalClient;
import com.Servindustria.WebPage.Modules.ClientAccount.Model.ClientAccount;
import com.Servindustria.WebPage.Modules.ClientAccount.Repository.ClientAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService{

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final ClientAccountRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<AuthResponse> login(LoginRequest request) {
        logger.info("Intentando autenticación para el usuario: {}", request.getEmail());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        logger.info("Autenticación exitosa para el usuario: {}", request.getEmail());
        UserDetails user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);

        ResponseCookie cookie = ResponseCookie.from("token", token)
            .httpOnly(true)
            .secure(false)
            .path("/")
            .maxAge(24 * 60 * 60)
            .sameSite("Lax")
            .build();
        

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(AuthResponse.builder().token(token).build());
    }

    public ResponseEntity<AuthResponse> register(RegisterRequest request) {
        logger.info("Intentando registrar usuario con email: {}", request.getEmail());
        validateRegisterRequest(request);

        Optional<ClientAccount> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("El email " + request.getEmail() + " ya esta registrado");
        }

        if (!"CLIENT".equalsIgnoreCase(request.getRole())) {
            throw new IllegalArgumentException("Rol no válido: " + request.getRole() + ". Solo se permite el rol CLIENT");
        }

        ClientAccount user = ClientAccount.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.valueOf(request.getRole().toUpperCase()))
            .build();

        Client client;
        if (request.getNameReasonSoc() != null && !request.getNameReasonSoc().isEmpty()) {
            CompanyClient companyClient = new CompanyClient();
            companyClient.setCode("CLI-" + UUID.randomUUID().toString().substring(0, 8));
            companyClient.setDocumentType(request.getDocumentType());
            companyClient.setDocumentNumber(request.getDocumentNumber());
            companyClient.setPhone1(request.getPhone1());
            companyClient.setPhone2(request.getPhone2());
            companyClient.setEmail(request.getEmail());
            companyClient.setNameReasonSoc(request.getNameReasonSoc());
            companyClient.setAddress(request.getAddress());
            companyClient.setReference(request.getReference());
            companyClient.setAccount(user);
            client = companyClient;
        } else {
            NaturalClient naturalClient = new NaturalClient();
            naturalClient.setCode("CLI-" + UUID.randomUUID().toString().substring(0, 8));
            naturalClient.setDocumentType(request.getDocumentType());
            naturalClient.setDocumentNumber(request.getDocumentNumber());
            naturalClient.setPhone1(request.getPhone1());
            naturalClient.setPhone2(request.getPhone2());
            naturalClient.setEmail(request.getEmail());
            naturalClient.setName(request.getName());
            naturalClient.setLastName(request.getLastName());
            naturalClient.setBirthDay(request.getBirthDay());
            naturalClient.setGender(request.getGender());
            naturalClient.setAccount(user);
            client = naturalClient;
        }

        user.setClient(client);
        // Guardar ClientAccount (y Client gracias al cascade en la relación)
        userRepository.save(user);
        logger.info("Usuario registrado exitosamente: {}", request.getEmail());

        // Generar token JWT
        String token = jwtService.getToken(user);

        ResponseCookie cookie = ResponseCookie.from("token", token)
            .httpOnly(true)
            .secure(false)
            .path("/")
            .maxAge(24 * 60 * 60)
            .sameSite("Lax")
            .build();
        

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(AuthResponse.builder().token(token).build());
    }

    private void validateRegisterRequest(RegisterRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        if (request.getRole() == null || request.getRole().isEmpty()) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }
        if (request.getDocumentType() == null || request.getDocumentType().isEmpty()) {
            throw new IllegalArgumentException("El tipo de documento es obligatorio");
        }
        if (request.getDocumentNumber() == null || request.getDocumentNumber().isEmpty()) {
            throw new IllegalArgumentException("El número de documento es obligatorio");
        }
        if (request.getPhone1() == null || request.getPhone1().isEmpty()) {
            throw new IllegalArgumentException("El teléfono 1 es obligatorio");
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }

        // Validar campos específicos según el tipo de cliente
        if (request.getNameReasonSoc() != null && !request.getNameReasonSoc().isEmpty()) {
            // CompanyClient
            if (request.getAddress() == null || request.getAddress().isEmpty()) {
                throw new IllegalArgumentException("La dirección es obligatoria para clientes empresariales");
            }
        } else {
            // NaturalClient
            if (request.getName() == null || request.getName().isEmpty()) {
                throw new IllegalArgumentException("El nombre es obligatorio para clientes naturales");
            }
            if (request.getLastName() == null || request.getLastName().isEmpty()) {
                throw new IllegalArgumentException("El apellido es obligatorio para clientes naturales");
            }
            if (request.getBirthDay() == null) {
                throw new IllegalArgumentException("La fecha de nacimiento es obligatoria para clientes naturales"); 
            }
            if (request.getGender() == null) {
                throw new IllegalArgumentException("El género es obligatorio para clientes naturales");
            }
        }
    }

    public ResponseEntity<AuthResponse> isAuthenticated(String token) {
        if(token == null || token.isEmpty() || !jwtService.isTokenValid(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthResponse.builder().token(null).build());
        }else{
            String email = jwtService.getEmailFromToken(token);
            userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el email: " + email));
            return ResponseEntity.ok().body(AuthResponse.builder().token(token).build());
        }
        
    }

    public ResponseEntity<AuthResponse> logout(String token) {
        if(token == null || token.isEmpty() || !jwtService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthResponse.builder().token(null).build());
        }else{
            ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0) // Expira inmediatamente
                .sameSite("Lax")
                .build();
            
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(AuthResponse.builder().token(null).build());
            }}
} 

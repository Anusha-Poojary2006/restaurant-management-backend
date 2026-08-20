package com.restaurant.restaurant_backend.controller;

import com.restaurant.restaurant_backend.model.User;
import com.restaurant.restaurant_backend.service.AuthService;
import com.restaurant.restaurant_backend.service.JwtService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(
            AuthService authService,
            JwtService jwtService) {

        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestBody User user) {

        try {
            User savedUser = authService.signup(user);

            savedUser.setPassword(null);

            return ResponseEntity.ok(savedUser);

        } catch (RuntimeException e) {

            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "message",
                            e.getMessage()
                    ));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        try {
            User user = authService.login(
                    request.getEmail(),
                    request.getPassword()
            );

            String token = jwtService.generateToken(
                    user.getEmail(),
                    user.getRole()
            );

            return ResponseEntity.ok(
                    Map.of(
                            "token", token,
                            "role", user.getRole(),
                            "email", user.getEmail()
                    )
            );

        } catch (RuntimeException e) {

            return ResponseEntity.status(401)
                    .body(Map.of(
                            "message",
                            e.getMessage()
                    ));
        }
    }

    public static class LoginRequest {

        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
package org.example.mrdverkin.controllers.api;

import jakarta.servlet.http.HttpServletRequest;
import org.example.mrdverkin.dto.LoginRequest;
import org.example.mrdverkin.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@RestController
@RequestMapping("/api")

public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @Autowired
    public AuthController(@Lazy AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest, HttpServletRequest httpRequest) {
        logger.info("Login attempt with username: {} and password: {}", loginRequest.getUsername(), loginRequest.getPassword());

        boolean succes = authService.login(loginRequest.getUsername(), loginRequest.getPassword());

        if (succes) {
            httpRequest.getSession(true);
            return ResponseEntity.ok(Map.of("message", "Login Successful"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
        }
    }
}


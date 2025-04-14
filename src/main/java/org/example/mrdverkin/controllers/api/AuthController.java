package org.example.mrdverkin.controllers.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.mrdverkin.dto.LoginRequest;
import org.example.mrdverkin.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
//@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(@Lazy AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest,  HttpServletRequest httpRequest) {
        boolean succes = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (succes) {
            httpRequest.getSession(true);
            return ResponseEntity.ok("Login Successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}


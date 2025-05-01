package org.example.mrdverkin.controllers.api.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LogoutController {

    @GetMapping("/logout")
    public ResponseEntity<String> logoutSuccess() {
        return ResponseEntity.ok("Вы успешно вышли из системы");
    }
}

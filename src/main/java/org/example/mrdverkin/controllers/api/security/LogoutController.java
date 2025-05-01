package org.example.mrdverkin.controllers.api.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api")
public class LogoutController {

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        // Получаем сессию, если она есть
        var session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // уничтожаем сессию
        }
        SecurityContextHolder.clearContext(); // очищаем контекст безопасности
        return ResponseEntity.ok("Вы успешно вышли из системы");
    }
}

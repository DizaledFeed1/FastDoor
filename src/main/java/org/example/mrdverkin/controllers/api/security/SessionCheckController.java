package org.example.mrdverkin.controllers.api.security;

import org.example.mrdverkin.dto.SessionStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionCheckController {

    @GetMapping("/api/check-session")
    public ResponseEntity<?> checkSession() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            // Пользователь не залогинен
            return ResponseEntity.status(401).body("Unauthorized");
        }

        // Если залогинен, вернуть роль и другие данные
        String role = auth.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElse("ROLE_UNKNOWN");

        switch (role) {
            case "ROLE_SELLER": role = "salespeople"; break;
            case "ROLE_ADMIN": role = "administrator"; break;
        }

        return ResponseEntity.ok(new SessionStatus(true, role));
    }
}
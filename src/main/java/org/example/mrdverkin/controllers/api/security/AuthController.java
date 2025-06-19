package org.example.mrdverkin.controllers.api.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.mrdverkin.dto.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")

public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private org.springframework.security.web.authentication.RememberMeServices rememberMeServices;



    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {
        List<String> answer = new ArrayList<>();

        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

            Authentication authentication = authenticationManager.authenticate(authToken);

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            List<String> roles = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            for (String role : roles) {
                if (role.equals("ROLE_ADMIN")) {
                    answer.add("administrator");
                }
                else if(role.equals("ROLE_SELLER")) {
                    answer.add("salespeople");
                }
                else if(role.equals("ROLE_MainInstaller")) {
                    answer.add("main");
                }
            }

            // Устанавливаем аутентификацию в SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (Boolean.TRUE.equals(loginRequest.isRememberMe())) {
                rememberMeServices.loginSuccess(request, response, authentication);
            }

            request.getSession(true)
                    .setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                            SecurityContextHolder.getContext());


            return ResponseEntity.ok(Map.of("message", "Login Successful",
                    "roles", String.join(", ", answer)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
        }
    }
}


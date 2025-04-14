package org.example.mrdverkin.security;

import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@Controller
@RequestMapping("/register")
public class RegistrationController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Map<String, String>>processRegistration(@RequestBody RegistrationForm form) {
        if (!form.isPasswordMatching()){
            return ResponseEntity.ok(Map.of("message", "Failed registration"));

        }
        userRepo.save(form.toUser(passwordEncoder));
        return ResponseEntity.ok(Map.of("message", "Login Successful"));

    }
}
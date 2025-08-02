package org.example.mrdverkin.controllers.api.security;

import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dto.RegistrationForm;
import org.example.mrdverkin.services.SellerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@Controller
@RequestMapping("/api/register")
public class RegistrationController {

    private final UserRepository userRepo;
    private final SellerService sellerService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepo, SellerService sellerService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.sellerService = sellerService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> processRegistration(@RequestBody RegistrationForm form) {
        if (!form.isPasswordMatching()){
            return ResponseEntity
                    .badRequest() // возвращает статус 400
                    .body(Map.of("message", "Passwords do not match"));
        }
        try {
            sellerService.setRoles(form.getRole());
            userRepo.save(form.toUser(passwordEncoder));
            return ResponseEntity.ok(Map.of("message", "Register Successful"));
        }
        catch (Exception e){}
        return ResponseEntity.badRequest().body(Map.of("message", "Invalid role"));
    }
}
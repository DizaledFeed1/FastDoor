package org.example.mrdverkin.controllers.api.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.mrdverkin.dto.RegistrationForm;
import org.example.mrdverkin.dto.auth.InviteRegistrationRequestDto;
import org.example.mrdverkin.dto.auth.InviteRegistrationResponseDto;
import org.example.mrdverkin.services.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@AllArgsConstructor
@RequestMapping("/api/register")
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping("/user")
    public InviteRegistrationResponseDto validateInviteCode(@RequestParam @JsonProperty("invite_code") String inviteCode) {
        return registrationService.inviteRegistration(inviteCode);
    }


    @PostMapping
    public ResponseEntity<Map<String, String>> processRegistration(@RequestBody @Valid RegistrationForm form) {
        if (!form.isPasswordMatching()){
            return ResponseEntity
                    .badRequest() // возвращает статус 400
                    .body(Map.of("message", "Passwords do not match"));
        }
        try {
            registrationService.registration(form);
            return ResponseEntity.ok(Map.of("message", "Register Successful"));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid role"));
        }
    }
}
package org.example.mrdverkin.controllers.api.bot;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.example.mrdverkin.dto.SmsRequest;
import org.example.mrdverkin.services.BotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sms")
@AllArgsConstructor
public class BotController {

    private final BotService botService;

    @PostMapping("/sendVerificationMessage")
    @Operation(description = "Отправка кода подтверждения")
    public ResponseEntity<Void> sendVerificationMessage(@RequestBody SmsRequest smsRequest) {
        botService.sendVerificationMessageIntegration(smsRequest);
        return ResponseEntity.ok().build();
    }
}

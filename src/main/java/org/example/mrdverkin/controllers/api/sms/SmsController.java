package org.example.mrdverkin.controllers.api.sms;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.example.mrdverkin.dto.sms.SmsRequest;
import org.example.mrdverkin.dto.sms.SmsResponse;
import org.example.mrdverkin.services.SmsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sms")
@AllArgsConstructor
@ConditionalOnProperty(name = "sms.enabled", havingValue = "true")
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/sendVerificationMessage")
    @Operation(description = "Отправка кода подтверждения")
    public ResponseEntity<SmsResponse> sendVerificationMessage(@RequestBody SmsRequest smsRequest) {
        return ResponseEntity.ok(smsService.sendVerificationMessageIntegration(smsRequest));
    }
}

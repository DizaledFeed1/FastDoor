package org.example.mrdverkin.stub.api;

import lombok.AllArgsConstructor;
import org.example.mrdverkin.stub.dto.sms.SmsResponseDto;
import org.example.mrdverkin.stub.service.SmsStubService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@Profile("dev")
@RestController
@AllArgsConstructor
@RequestMapping("/stub/sms")
public class SmsControllerStub {

    private final SmsStubService smsStubService;

    @PostMapping("/send")
    public SmsResponseDto sendSms(@RequestParam String project,
                                  @RequestParam String recipients,
                                  @RequestParam String message,
                                  @RequestParam String apikey) {
        return smsStubService.sendSms(project, recipients, message, apikey);
    }
}

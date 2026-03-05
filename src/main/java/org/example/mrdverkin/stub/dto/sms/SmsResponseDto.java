package org.example.mrdverkin.stub.dto.sms;

import org.springframework.context.annotation.Profile;

@Profile("dev")
public interface SmsResponseDto {
    String getStatus();
}

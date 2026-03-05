package org.example.mrdverkin.stub.dto.sms;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Profile;

@Data
@Builder
@Profile("dev")
public class SmsErrorResponseStubDto implements SmsResponseDto{
    private String status;
    private Integer error;
    private String message;
}

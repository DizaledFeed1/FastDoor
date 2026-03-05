package org.example.mrdverkin.stub.service;

import org.example.mrdverkin.stub.dto.sms.SmsErrorResponseStubDto;
import org.example.mrdverkin.stub.dto.sms.SmsResponseDto;
import org.example.mrdverkin.stub.dto.sms.SmsSuccessResponseStubDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Profile("dev")
public class SmsStubService {

    public SmsResponseDto sendSms(String project, String recipients, String message, String apikey) {
        Integer error = 0;
        String errorMessage = null;
        if (!project.equals("test")){
            error = 1;
            errorMessage = "project not found";
        }
        else if (!apikey.equals("123asx")) {
            error = 2;
            errorMessage = "invalid signature or message encoding is not utf8";
        }

        if (errorMessage!=null){
            return SmsErrorResponseStubDto.builder()
                    .status("error")
                    .error(error)
                    .message(errorMessage)
                    .build();
        }

        return SmsSuccessResponseStubDto.builder()
                .status("success")
                .recipients(List.of(recipients))
                .parts(1)
                .count(1)
                .price(recipients.isEmpty() ? "0,0" : "7,0")
                .balance(BigDecimal.TEN)
                .messages_id(List.of(410599590L))
                .test(1)
                .build();
    }
}

package org.example.mrdverkin.stub.dto.sms;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Profile("dev")
public class SmsSuccessResponseStubDto implements SmsResponseDto{
    private String status;
    private List<String> recipients;
    private Integer parts;
    private Integer count;
    private String price;
    private BigDecimal balance;
    private List<Long> messages_id;
    private Integer test;
}

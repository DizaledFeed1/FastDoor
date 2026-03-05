package org.example.mrdverkin.dto.sms;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsResponse {
    private String status;

    // Только для success
    private List<String> recipients;
    private Integer parts;
    private Integer count;
    private String price;
    private String balance;
    private List<Long> messages_id;
    private Integer test;

    // Только для error
    private Integer error;
    private String message;
}
package org.example.mrdverkin.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mrdverkin.property.SmsProperty;
import org.example.mrdverkin.dto.sms.SmsRequest;
import org.example.mrdverkin.dto.sms.SmsResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class SmsService {

    private RestTemplate restTemplate;
    private final SmsProperty smsProperty;

    public SmsResponse sendVerificationMessageIntegration(SmsRequest smsRequest) {

        for (int i = 0; i < 5; i++) {
            SmsResponse body = sendIntegration(smsRequest);
            if ("success".equals(Objects.requireNonNull(body).getStatus()) && !body.getPrice().equals("0,0")) {
                log.info("Результат отправки кода: {}", body);
                return body;
            } else if ("error".equals(Objects.requireNonNull(body).getStatus())) {
                log.error("Ошибка отправки: {}", body.getMessage());
                return body;
            }
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_GATEWAY,
                "Не удалось отправить SMS после 5 попыток"
        );
    }

    private SmsResponse sendIntegration(SmsRequest smsRequest) {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("project", smsProperty.getProject());
        formData.add("recipients", smsRequest.getPhoneNumber());
        formData.add("message", smsRequest.getCode());
        formData.add("apikey", smsProperty.getApiKey());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

        ResponseEntity<SmsResponse> response = restTemplate.exchange(
                smsProperty.getUrl(),
                HttpMethod.POST,
                requestEntity,
                SmsResponse.class
        );
        return response.getBody();
    }
}

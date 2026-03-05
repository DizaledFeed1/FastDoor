package org.example.mrdverkin.service;

import org.example.mrdverkin.MrDverkinApplication;
import org.example.mrdverkin.dto.sms.SmsRequest;
import org.example.mrdverkin.dto.sms.SmsResponse;
import org.example.mrdverkin.services.SmsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = MrDverkinApplication.class)
@ActiveProfiles("dev")
public class SmsServiceTest {
    @Autowired
    private SmsService smsService;
    @MockBean
    private RestTemplate restTemplate;

    SmsRequest request;

    @Test
    public void sendVerificationMessageIntegrationSuccessTest(){
        request = SmsRequest.builder()
                .phoneNumber("+79832149360")
                .code("123456")
                .build();

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(SmsResponse.class)
        )).thenReturn(ResponseEntity.ok(SmsResponse.builder()
                .status("success")
                .recipients(List.of("+79832149360"))
                .parts(1)
                .count(1)
                .price("16")
                .balance("100")
                .messages_id(List.of(36344L))
                .test(1)
                .build()));

        SmsResponse response = smsService.sendVerificationMessageIntegration(request);

        assertEquals("success", response.getStatus());
        assertEquals("+79832149360", response.getRecipients().get(0));
        assertNotNull(response.getParts());
        assertNotNull(response.getCount());
        assertNotNull(response.getPrice());
        assertNotNull(response.getBalance());
        assertNotNull(response.getMessages_id());
        assertNotNull(response.getTest());

        assertNull(response.getError());
        assertNull(response.getMessage());
    }

    @Test
    public void sendVerificationMessageIntegrationFailureTest(){
        request = SmsRequest.builder()
                .phoneNumber("+79832149360")
                .code("123456")
                .build();

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(SmsResponse.class)
        )).thenReturn(ResponseEntity.ok(SmsResponse.builder()
                .status("success")
                .recipients(List.of("+79832149360"))
                .parts(1)
                .count(1)
                .price("0,0")
                .balance("100")
                .messages_id(List.of(36344L))
                .test(1)
                .build()));

        assertThrows(ResponseStatusException.class, () -> smsService.sendVerificationMessageIntegration(request));
    }
}
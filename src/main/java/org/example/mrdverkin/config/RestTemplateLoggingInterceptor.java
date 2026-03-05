package org.example.mrdverkin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RestTemplateLoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {

        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);

        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) {
        log.info("➡️ HTTP REQUEST");
        log.info("URI     : {}", request.getURI());
        log.info("Method  : {}", request.getMethod());
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        log.info("⬅️ HTTP RESPONSE");
        log.info("Status  : {}", response.getStatusCode());
        log.info("Headers : {}", response.getHeaders());
    }
}


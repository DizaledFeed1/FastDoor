package org.example.mrdverkin.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties(prefix = "sms")
@Validated
@Getter
@Setter
public class SmsConfig {
    /**
     * Включатель
     */
    @NotNull
    Boolean enabled;

    /**
     * Название проекта
     */
    @NotBlank
    String project;

    /**
     * API токен
     */
    @NotBlank
    String apiKey;

    /**
     * Базовый URL
     */
    @NotBlank
    String url;
}

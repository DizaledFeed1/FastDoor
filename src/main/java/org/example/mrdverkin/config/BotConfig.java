package org.example.mrdverkin.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sms")
@Getter
@Setter
public class BotConfig {
    /**
     * Название проекта
     */
    String project;

    /**
     * API токен для Telegram Gateway
     */
    @NotBlank
    private String apiKey;

    /**
     * Базовый URL Telegram Gateway
     */
    @NotBlank
    private String url;
}

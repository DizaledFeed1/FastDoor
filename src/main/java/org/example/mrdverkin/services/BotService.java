package org.example.mrdverkin.services;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.example.mrdverkin.config.BotConfig;
import org.example.mrdverkin.dataBase.Entitys.Installer;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dto.SmsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class BotService {
    @Autowired
    private RestTemplate restTemplate;

    private final BotConfig botConfig;

    private static final Logger logger = LoggerFactory.getLogger(BotService.class);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static HttpHeaders tgHeaders;

    @PostConstruct
    public void init() {
        tgHeaders = new HttpHeaders();
        tgHeaders.setContentType(MediaType.APPLICATION_JSON);
    }


    public void selectMessage(Order order) {
        String phoneNumber = order.getInstaller().getPhone();
        String message = "Вам назначен заказ по адресу: " + order.getAddress() +
                "\\nДата: " + order.getDateOrder().format(formatter) +
                "\\nКоличество входных дверей: " + order.getFrontDoorQuantity() +
                "\\nКоличество межкомнатных дверей: " + order.getInDoorQuantity() +
                "\\nКомментарий от установщика: " + (order.getMessageMainInstaller() != null ? order.getMessageMainInstaller() : "Нет") +
                "\\nКомментарий от продавца: " + (order.getMessageSeller() != null ? order.getMessageSeller() : "Нет");

        sendMessage(phoneNumber, message);
    }

    public void modificationMessage(Order newOrder, Order oldOrder) {
        String phoneNumber = oldOrder.getInstaller().getPhone();
        String message = "Ваш  заказ по адресу: " + oldOrder.getAddress() +
                "\\nДата: " + oldOrder.getDateOrder() +

                "\\nИзменён\\nНовые данные:" +
                "\\nАдрес:" + newOrder.getAddress() +
                "\\nДата: " + newOrder.getDateOrder().format(formatter) +
                "\\nКоличество входных дверей: " + newOrder.getFrontDoorQuantity() +
                "\\nКоличество межкомнатных дверей: " + newOrder.getInDoorQuantity() +
                "\\nКомментарий от установщика: " + (newOrder.getMessageMainInstaller() != null ? newOrder.getMessageMainInstaller() : "Нет") +
                "\\nКомментарий от продавца: " + (newOrder.getMessageSeller() != null ? newOrder.getMessageSeller() : "Нет");

        sendMessage(phoneNumber, message);
    }

    public void deleteMessage(Order order) {
        Installer installer = order.getInstaller();
        if (installer != null) {
            String phoneNumber = installer.getPhone();

            String message = "Ваш заказ по адресу: " + order.getAddress() +
                    "\\nДата: " + order.getDateOrder().format(formatter) +
                    "\\nОтменён";

            sendMessage(phoneNumber, message);
        }
    }

    public void sendMessage(String phoneNumber, String message) {
        try {

            // Создаем JSON-сообщение
            String json = "{ \"phoneNumber\": \"" + phoneNumber + "\", \"message\": \"" + message + "\" }";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<String> entity = new HttpEntity<>(json, headers);

            // Отправляем POST-запрос
            String url = "http://localhost:3000/send-message";
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            logger.info("Ответ от сервера: {}", response.getBody());

        } catch (HttpClientErrorException e) {
            logger.error("Ошибка HTTP: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            logger.error("Ошибка сети: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Произошла ошибка при отправке сообщения: {}", e.getMessage());
        }
    }

    public void sendVerificationMessageIntegration(SmsRequest smsRequest) {

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("project", botConfig.getProject());
        formData.add("recipients", smsRequest.getPhoneNumber());
        formData.add("message", smsRequest.getCode());
        formData.add("apikey", botConfig.getApiKey());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                botConfig.getUrl(),
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        logger.info("Результат отправки кода: {}", response.getBody());
    }
}

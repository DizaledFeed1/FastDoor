package org.example.mrdverkin.services;

import org.example.mrdverkin.dataBase.Entitys.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class BotService {
    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(BotService.class);

    public void selectMessage(Order order) {
        String phoneNumber = order.getInstaller().getPhone();
        String message = "Вам назначен заказ по адресу: " + order.getAddress() +
                "\\nДата: " + order.getDateOrder() +
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
                "\\nДата: " + newOrder.getDateOrder() +
                "\\nКоличество входных дверей: " + newOrder.getFrontDoorQuantity() +
                "\\nКоличество межкомнатных дверей: " + newOrder.getInDoorQuantity() +
                "\\nКомментарий от установщика: " + (newOrder.getMessageMainInstaller() != null ? newOrder.getMessageMainInstaller() : "Нет") +
                "\\nКомментарий от продавца: " + (newOrder.getMessageSeller() != null ? newOrder.getMessageSeller() : "Нет");
    }

    public void deleteMessage(Order order) {
        String phoneNumber = order.getInstaller().getPhone();
        String message = "Ваш заказ по адресу: " + order.getAddress() +
                "\\nДата: " + order.getDateOrder() +
                "\\nОтменён";

        sendMessage(phoneNumber, message);
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
}

package org.example.mrdverkin.services;

import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Repository.InstallerRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
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
public class MainInstallerService {
    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(MainInstallerService.class);

    /**
     * Отправляет сообщение установщику с комментариями от продавца и главного установщика.
     * @param order заказ, содержащий информацию о номере телефона установщика и комментариях
     */
    public void sendMessage(Order order) {
//        Order order = orderRepository.findByOrderId(id);
        try {
            String phoneNumber = order.getInstaller().getPhone();
            String message = "Вам назначен заказ по адресу: " + order.getAddress() +
                    "\\nДата: " + order.getDateOrder() +
                    "\\nКоличество входных дверей: " + order.getFrontDoorQuantity() +
                    "\\nКоличество межкомнатных дверей: " + order.getInDoorQuantity() +
                    "\\nКомментарий от установщика: " + (order.getMessageMainInstaller() != null ? order.getMessageMainInstaller() : "Нет") +
                    "\\nКомментарий от продавца: " + (order.getMessageSeller() != null ? order.getMessageSeller() : "Нет");

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

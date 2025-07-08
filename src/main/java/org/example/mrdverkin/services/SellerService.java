package org.example.mrdverkin.services;

import org.example.mrdverkin.dataBase.Entitys.*;
import org.example.mrdverkin.dataBase.Repository.DoorLimitsRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dto.DateAvailability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
/**
 * Класс сервис для обработки запросов продавца.
 * @author Кирилл Селявский
 * @version 1.1
 */
public class SellerService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoorLimitsRepository doorLimitsRepository;

    private static final Logger logger = LoggerFactory.getLogger(SellerService.class);

    /**
     * Метод для проверки введённых значений пользователя.
     * @param order объект заказа
     * @return объект BindingResult с возможными ошибками
     */
    public boolean checkcreate( Order order, DoorLimits doorLimits) {
        //Проверяем коректность ввода
        LocalDate today = doorLimits.getLimitDate().toLocalDate();
        DateAvailability availability = orderRepository.getDoorCountsByDate(today, Condition.DELETED);
        if (availability == null) {
            availability = new DateAvailability(today,0L,0L, true);
            logger.error("Попытались добавить заказ на тот день где нет DoorLimits");
        }
        availability.setFrontDoorQuantity(availability.getFrontDoorQuantity() + order.getFrontDoorQuantity());
        availability.setInDoorQuantity(availability.getInDoorQuantity() + order.getInDoorQuantity());
        if (availability.getFrontDoorQuantity() > doorLimits.getFrontDoorQuantity() ||
                availability.getInDoorQuantity() > doorLimits.getInDoorQuantity()) {
            return false;
        }
        return true;
    }

    public ResponseEntity<Map<String, String>> create(Order order, User user) {

        DoorLimits doorLimits = doorLimitsRepository.findByLimitDate(order.getDoorLimits().getLimitDate());

        if (checkcreate(order,doorLimits)) {
            order.setDoorLimits(doorLimits);
            order.setUser(user);
            orderRepository.save(order);
            userRepository.save(user);
            return ResponseEntity.ok(Map.of("message", "Order created"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Неверные данные"));
    }

    /**
     * Метод для проверки корректности введённых пользователем ролей
     * @param role
     * @exception IllegalArgumentException
     */
    public void setRoles(String role) {
        if (!Role.isValidRole(role)) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}

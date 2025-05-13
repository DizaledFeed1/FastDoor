package org.example.mrdverkin.services;

import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.Role;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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

    /**
     * Метод для проверки введённых значений пользователя.
     * @param order объект заказа
     * @return объект BindingResult с возможными ошибками
     */
    public boolean check( Order order) {
        //Проверяем коректность ввода
        if ((orderRepository.numberOfFrontDoorsToInstallation(order.getDateOrder()) - order.getFrontDoorQuantity()) < 0) {
            return false;
//            bindingResult.addError(new FieldError("order", "frontDoorQuantity", "Превышен лимит входных дверей на этот день"));
        }
        if ((orderRepository.numberOfInDoorsToInstallation(order.getDateOrder()) - order.getInDoorQuantity()) < 0) {
            return false;
//            bindingResult.addError(new FieldError("order", "inDoorQuantity", "Превышен лимит межкомнатных дверей на этот день"));
        }
        return true;
    }

    public ResponseEntity<Map<String, String>> create(Order order, User user) {

        if (check(order)) {
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

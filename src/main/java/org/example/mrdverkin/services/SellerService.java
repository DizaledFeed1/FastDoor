package org.example.mrdverkin.services;

import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.Role;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
/**
 * Класс сервис для обработки запросов продавца.
 * @author Кирилл Селявский
 * @version 1.0
 */
public class SellerService {
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Метод для проверки введённых значений пользователя.
     * @param bindingResult объект для хранения ошибок валидации
     * @param order объект заказа
     * @return объект BindingResult с возможными ошибками
     */
    public BindingResult check(BindingResult bindingResult, Order order) {
        //Проверяем коректность ввода
        if ((orderRepository.numberOfFrontDoorsToInstallation(order.getDateOrder()) - order.getFrontDoorQuantity()) < 0) {
            bindingResult.addError(new FieldError("order", "frontDoorQuantity", "Превышен лимит входных дверей на этот день"));
        }
        if ((orderRepository.numberOfInDoorsToInstallation(order.getDateOrder()) - order.getInDoorQuantity()) < 0) {
            bindingResult.addError(new FieldError("order", "inDoorQuantity", "Превышен лимит межкомнатных дверей на этот день"));
        }
        return bindingResult;
    }

    // Проверяем роли на допустимость
    public void setRoles(String role) {
        if (!Role.isValidRole(role)) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}

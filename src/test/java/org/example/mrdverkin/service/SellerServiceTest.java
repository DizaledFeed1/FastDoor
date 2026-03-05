package org.example.mrdverkin.service;

import org.example.mrdverkin.MrDverkinApplication;
import org.example.mrdverkin.dataBase.Entitys.DoorLimits;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.Role;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.DoorLimitsRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.services.SellerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MrDverkinApplication.class)
@ActiveProfiles("dev")
class SellerServiceTest {

    @Autowired
    private SellerService sellerService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoorLimitsRepository doorLimitsRepository;
    @Autowired
    private OrderRepository orderRepository;

    private DoorLimits doorLimits;
    private User user;


    @BeforeEach
    void init() {
        doorLimitsRepository.deleteAll();
        orderRepository.deleteAll();
        userRepository.deleteAll();

        DoorLimits doorLimitsToSave = new DoorLimits();
        java.util.Date utilDate = new java.util.Date();
        doorLimitsToSave.setLimitDate(new java.sql.Date(utilDate.getTime()));
        doorLimitsToSave.setFrontDoorQuantity(2);
        doorLimitsToSave.setInDoorQuantity(10);


        user = userRepository.save(new User("test", "test", "shop", Collections.singleton(Role.ROLE_SELLER)));
        doorLimits = doorLimitsRepository.save(doorLimitsToSave);
    }

    @Test
    void createSuccessTest(){
        assertTrue(orderRepository.findAll().isEmpty(), "Список заказов не пустой при старте теста");

        Order order = new Order();
        order.setFrontDoorQuantity(2);
        order.setInDoorQuantity(10);
        order.setDoorLimits(doorLimits);

        sellerService.create(order, user);
        assertFalse(orderRepository.findAll().isEmpty(), "Заказ не создан");
    }

    @Test
    void createSuccessFailTest(){
        assertTrue(orderRepository.findAll().isEmpty(), "Список заказов не пустой при старте теста");

        Order order = new Order();
        order.setFrontDoorQuantity(12);
        order.setInDoorQuantity(20);
        order.setDoorLimits(doorLimits);

        sellerService.create(order, user);
        assertTrue(orderRepository.findAll().isEmpty(), "Список заказов не пустой при завершении теста");
    }
}

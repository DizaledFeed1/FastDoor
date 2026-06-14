package org.example.mrdverkin.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.mrdverkin.dataBase.Entitys.Condition;
import org.example.mrdverkin.dataBase.Entitys.Installer;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.InstallerRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dto.installer.OrderApproveResponseDto;
import org.example.mrdverkin.dto.installer.OrderCancelRequestDto;
import org.example.mrdverkin.dto.installer.OrderCancelResponseDto;
import org.example.mrdverkin.services.InstallerOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
@Sql(scripts = "/data/full_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class InstallerOrderServiceTest {
    @Autowired
    private InstallerOrderService installerOrderService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InstallerRepository installerRepository;

    private User testUser;
    private User otherUser;
    private final UUID ORDER_ID = UUID.fromString("30000000-0000-4000-a000-000000000003");
    private final UUID NOT_FOUND_UUID = UUID.fromString("30000000-0000-4000-a000-000000000004");
    private final UUID CONDITION_UUID = UUID.fromString("10000000-0000-4000-a000-000000000001");

    @BeforeEach
    public void setUp() {
        testUser = userRepository.findById(UUID.fromString("55555555-5555-4555-8555-555555555555"))
                .orElseThrow(() -> new EntityNotFoundException("Установщик не найден"));
        otherUser = userRepository.findById(UUID.fromString("66666666-6666-4666-8666-666666666666"))
                .orElseThrow(() -> new EntityNotFoundException("Установщик не найден"));
    }

    @Test
    public void approveOrderTestApprove() {
        OrderApproveResponseDto response = installerOrderService.approveOrder(ORDER_ID, testUser);

        Order updateOrder = orderRepository.findById(ORDER_ID)
                .orElseThrow(() -> new EntityNotFoundException("Не найден заказ с указанным id"));

        assertEquals(response.getOrderId(), ORDER_ID);
        assertEquals(Condition.WORKED, response.getStatusOrder());
        assertEquals(Condition.WORKED, updateOrder.getCondition());
    }

    @Test
    public void approveOrderTestNotFoundException() {
        assertThrows(EntityNotFoundException.class, () ->
                installerOrderService.approveOrder(NOT_FOUND_UUID, testUser));
    }

    @Test
    public void approveOrderTestIllegalArgumentExceptionUser() {
        assertThrows(IllegalArgumentException.class, () ->
                installerOrderService.approveOrder(ORDER_ID, otherUser));
    }

    @Test
    public void approveOrderTestIllegalArgumentExceptionCondition() {
        assertThrows(IllegalArgumentException.class, () ->
                installerOrderService.approveOrder(CONDITION_UUID, testUser));
    }

    @Test
    @Transactional(readOnly = true)
    public void cancelOrderTestApprove() {
        Integer ordersCountBefore = installerRepository.findByUser(testUser).getOrders().size();

        OrderCancelResponseDto response = installerOrderService.cancelOrder(ORDER_ID, testUser,
                OrderCancelRequestDto.builder().
                comment("testComment")
                .build());

        Order updateOrder = orderRepository.findById(ORDER_ID)
                .orElseThrow(() -> new EntityNotFoundException("Не найден заказ с указанным id"));
        Installer installer = installerRepository.findByUser(testUser);

        assertEquals(response.getOrderId(), ORDER_ID);
        assertEquals(Condition.CANCELED, response.getStatusOrder());
        assertEquals(Condition.CANCELED, updateOrder.getCondition());
        assertNull(updateOrder.getInstaller());
        assertEquals(ordersCountBefore - 1, installer.getOrders().size());
        assertNotEquals(0 , updateOrder.getAnnotation().size());
    }

    @Test
    @Transactional(readOnly = true)
    public void cancelOrderTestNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> installerOrderService.cancelOrder(NOT_FOUND_UUID, testUser,
                OrderCancelRequestDto.builder().
                        comment("testComment")
                        .build()));
    }


    @Test
    @Transactional(readOnly = true)
    public void cancelOrderTestIllegalArgumentExceptionUser() {
        assertThrows(IllegalArgumentException.class, () -> installerOrderService.cancelOrder(CONDITION_UUID, otherUser,
                OrderCancelRequestDto.builder().
                        comment("testComment")
                        .build()));
    }

    @Test
    @Transactional(readOnly = true)
    public void cancelOrderTestIllegalArgumentExceptionCondition() {
        assertThrows(IllegalArgumentException.class, () -> installerOrderService.cancelOrder(CONDITION_UUID, testUser,
                OrderCancelRequestDto.builder().
                        comment("testComment")
                        .build()));
    }
}

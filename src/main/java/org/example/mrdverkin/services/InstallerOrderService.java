package org.example.mrdverkin.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.mrdverkin.dataBase.Entitys.*;
import org.example.mrdverkin.dataBase.Repository.InstallerRepository;
import org.example.mrdverkin.dataBase.Repository.OrderAnnotationRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dto.installer.OrderApproveResponseDto;
import org.example.mrdverkin.dto.installer.OrderCancelRequestDto;
import org.example.mrdverkin.dto.installer.OrderCancelResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class InstallerOrderService {

    private final OrderRepository orderRepository;
    private final InstallerRepository installerRepository;
    private final OrderAnnotationRepository orderAnnotationRepository;

    @Transactional
    public OrderApproveResponseDto approveOrder(UUID orderId, User installerUser) {
        Installer installer = installerRepository.findByUser(installerUser);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказ с id: " + orderId + "не найден"));

        validateOrder(order, installer);

        order.setCondition(Condition.WORKED);

        return OrderApproveResponseDto.builder()
                .orderId(order.getId())
                .statusOrder(order.getCondition())
                .build();
    }

    @Transactional
    public OrderCancelResponseDto cancelOrder(UUID orderId, User installerUser, OrderCancelRequestDto request) {
        Installer installer = installerRepository.findByUser(installerUser);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Заказ с id: " + orderId + "не найден"));

        validateOrder(order, installer);

        OrderAnnotation annotation = OrderAnnotation.builder()
                .order(order)
                .installer(installerUser)
                .annotation(request.getComment())
                .build();

        order.setInstaller(null);
        installer.getOrders().remove(order);

        order.setCondition(Condition.CANCELED);

        orderAnnotationRepository.save(annotation);

        order.getAnnotation().add(annotation);

        return OrderCancelResponseDto.builder()
                .orderId(order.getId())
                .statusOrder(order.getCondition())
                .build();
    }

    private void validateOrder(Order order, Installer installer) {
        if (!order.getInstaller().equals(installer)) {
            throw new IllegalArgumentException("Заказ с id: " + order.getId() + " не принадлежит установщику");
        }

        if (!Condition.ASSIGNED.equals(order.getCondition())) {
            throw new IllegalArgumentException("Статус заказа с id: " + order.getId() + " не валиден");
        }
    }
}

package org.example.mrdverkin.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.InstallerRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dto.OrderAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InstallerRepository installerRepository;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private UserRepository userRepository;

    public Order findOrderById(Long id) {
        return orderRepository.findByOrderId(id);
    }

    public ResponseEntity<Map<String, Object>> updateOrder(Long id, OrderAttribute orderAttribute) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order existingOrder = optionalOrder.get();

            // Обновляем поля заказа
            existingOrder.setFullName(orderAttribute.getFullName());
            existingOrder.setAddress(orderAttribute.getAddress());
            existingOrder.setPhone(orderAttribute.getPhone());
            existingOrder.setMessageSeller(orderAttribute.getMessageSeller());
            existingOrder.setMessageMainInstaller(orderAttribute.getMessageMainInstaller());
            existingOrder.setDateOrder(orderAttribute.getDateOrder());
            existingOrder.setFrontDoorQuantity(orderAttribute.getFrontDoorQuantity());
            existingOrder.setInDoorQuantity(orderAttribute.getInDoorQuantity());
            if (orderAttribute.getInstallerName() != null) {
                existingOrder.setInstaller(installerRepository.findByName(orderAttribute.getInstallerName()));
            }
            orderRepository.save(existingOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Map<String, Object>> deleteOrderById(User user, Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);

        if (orderOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Такой заказ не существует"));
        }

        Order order = orderOpt.get();
        if (!order.getUser().equals(user)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Юзер не принадлежит данному заказу"));
        }

        // Если заказ найден и юзер соответствует
        orderRepository.deleteById(id);  // Удаление заказа
        return ResponseEntity.ok().body(Map.of("message", "Заказ успешно удалён"));
    }

    public ResponseEntity<Map<String, Object>> searchOrderBySeller(String nickname, Pageable pageable, int page) {
        Optional<User> user = userRepository.findByNickname(nickname);
        if (user.isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("message", "Такого пользователя не существует"));
        }
        Page<Order> ordersByNickname = orderRepository.findOrdersByUser(user.get(), pageable);
        if (ordersByNickname.isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("message", "У данного пользователя нет созданых заказов"));
        }
        List<OrderAttribute> adminMapping = OrderAttribute.fromOrderList(ordersByNickname);

        Map<String, Object> response = new HashMap<>();
        response.put("orders", adminMapping);
        response.put("currentPage", page);
        response.put("totalPages", ordersByNickname.getTotalPages());

        return ResponseEntity.ok(response);
    }
}
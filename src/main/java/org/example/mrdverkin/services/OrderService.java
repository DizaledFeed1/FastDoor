package org.example.mrdverkin.services;

import org.example.mrdverkin.dataBase.Entitys.DoorLimits;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.DoorLimitsRepository;
import org.example.mrdverkin.dataBase.Repository.InstallerRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dto.OrderAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
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
    private DoorLimitsRepository doorLimitsRepository;
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

    @Scheduled(cron = "0 */1 * * * *")
    public void generateMonthlyLimits(){

        LocalDate startDate = LocalDate.now().withDayOfMonth(1).plusMonths(1);
        int defaultFrontDoors = 2;
        int defaultInDoors = 50;

        for (int i = 0; i < startDate.lengthOfMonth(); i++) {
            LocalDate date = startDate.plusDays(i);

            // Проверка: существует ли уже запись
            if (doorLimitsRepository.findByLimitDate(Date.valueOf(date)) != null) {
                continue;
            }

            DoorLimits limit = new DoorLimits();
            limit.setLimitDate(Date.valueOf(date));
            limit.setFrontDoorQuantity(defaultFrontDoors);
            limit.setInDoorQuantity(defaultInDoors);
            doorLimitsRepository.save(limit);
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Генерация!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

    }
}
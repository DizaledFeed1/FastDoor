package org.example.mrdverkin.services;

import org.example.mrdverkin.dataBase.Entitys.Condition;
import org.example.mrdverkin.dataBase.Entitys.DoorLimits;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.DoorLimitsRepository;
import org.example.mrdverkin.dataBase.Repository.InstallerRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dto.DateAvailability;
import org.example.mrdverkin.dto.OrderAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Сервис для управления заказами.
 */
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
    @Autowired
    private BotService botService;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    /**
     * Находит заказ по его ID.
     *
     * @param id ID заказа
     * @return заказ
     */
    public Order findOrderById(Long id) {
        return orderRepository.findByOrderId(id);
    }

    /**
     * Проверяет, возможно ли обновить заказ с новыми параметрами, не превышая лимиты.
     *
     * @param order     исходный заказ
     * @param attribute новые параметры заказа
     * @return true, если обновление допустимо; false в противном случае
     */
    public boolean checkUpdate(Order order, OrderAttribute attribute) {
        DoorLimits doorLimits = doorLimitsRepository.findByLimitDate(order.getDoorLimits().getLimitDate());

        DateAvailability dateAvailabilities = orderRepository.getDoorCountsByDate(attribute.getDateOrder(), Condition.DELETED);
        System.out.println(dateAvailabilities);
        //Проверяем есть ли заказы на этот день, если нет приравниваем значения к 0.
        if (dateAvailabilities == null){
            dateAvailabilities = new DateAvailability(attribute.getDateOrder(), 0L, 0L, true);
            logger.error("Попытались добавить заказ на тот день где нет DoorLimits");
        }

        //Если в заказе поменялась дата то к количестве дверей добавляем новые и проверяем.
        if (!order.getDateOrder().equals(attribute.getDateOrder())){
            dateAvailabilities.setInDoorQuantity(dateAvailabilities.getInDoorQuantity() + attribute.getInDoorQuantity());
            dateAvailabilities.setFrontDoorQuantity(dateAvailabilities.getFrontDoorQuantity() + attribute.getFrontDoorQuantity());
        }else {
            // Елси в заказе не поменялась дата то отнимаем старые значения и прибавляем новые.
            dateAvailabilities.setInDoorQuantity((dateAvailabilities.getInDoorQuantity() - order.getInDoorQuantity()) + attribute.getInDoorQuantity());
            dateAvailabilities.setFrontDoorQuantity((dateAvailabilities.getFrontDoorQuantity() - order.getFrontDoorQuantity()) + attribute.getFrontDoorQuantity());
        }

        if (dateAvailabilities.getFrontDoorQuantity() > doorLimits.getFrontDoorQuantity() ||
                dateAvailabilities.getInDoorQuantity() > doorLimits.getInDoorQuantity()) {
            return false;
        }

        return true;
    }

    /**
     * Обновляет заказ по ID с новыми атрибутами.
     *
     * @param id             ID заказа
     * @param orderAttribute новые параметры заказа
     * @return HTTP-ответ: OK — успешное обновление, BadRequest — превышение лимитов, NotFound — заказ не найден
     */
    public ResponseEntity<Map<String, Object>> updateOrder(Long id, OrderAttribute orderAttribute) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order existingOrder = optionalOrder.get();
            Order copy = existingOrder;

                // Обновляем поля заказа
            if (checkUpdate(existingOrder, orderAttribute)) {
                existingOrder.setFullName(orderAttribute.getFullName());
                existingOrder.setAddress(orderAttribute.getAddress());
                existingOrder.setPhone(orderAttribute.getPhone());
                existingOrder.setMessageSeller(orderAttribute.getMessageSeller());
//                existingOrder.setMessageMainInstaller(orderAttribute.getMessageMainInstaller());
                existingOrder.setDateOrder(orderAttribute.getDateOrder());
                existingOrder.setFrontDoorQuantity(orderAttribute.getFrontDoorQuantity());
                existingOrder.setInDoorQuantity(orderAttribute.getInDoorQuantity());

//                if (orderAttribute.getInstallerName() != null) {
//                    existingOrder.setInstaller(installerRepository.findByName(orderAttribute.getInstallerName()));
//                }

                orderRepository.save(existingOrder);
                if (existingOrder.getInstaller() != null) {
                    botService.modificationMessage(existingOrder, copy);
                }

            } else return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }


    /**
     * Удаляет заказ по ID, если он принадлежит указанному пользователю.
     *
     * @param id   ID заказа
     * @return HTTP-ответ с сообщением об успехе или ошибке
     */
    public ResponseEntity<Map<String, Object>> deleteOrderById(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);

        if (orderOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Такой заказ не существует"));
        }
        Order order = orderOpt.get();

//            if (!order.getUser().equals(user)) {
//                return ResponseEntity.badRequest().body(Map.of("message", "Юзер не принадлежит данному заказу"));
//            }

        order.setCondition(Condition.DELETED);
        orderRepository.save(order);
        botService.deleteMessage(order);
    return ResponseEntity.ok().body(Map.of("message", "Заказ успешно удалён"));
    }

    /**
     * Поиск заказов, созданных продавцом по его нику.
     *
     * @param nickname никнейм продавца
     * @param pageable параметры пагинации
     * @param page     текущая страница
     * @return список заказов и информация о страницах или сообщение об ошибке
     */
    public ResponseEntity<Map<String, Object>> searchOrderBySeller(String nickname, Pageable pageable, int page) {
        Optional<User> user = userRepository.findByNickname(nickname);
        if (user.isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("message", "Такого пользователя не существует"));
        }
        Page<Order> ordersByNickname = orderRepository.findOrdersByUserAll(user.get(), pageable);
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

    /**
     * Запланированная задача, которая создаёт лимиты на следующий месяц в начале каждого месяца.
     * Запускается 1-го числа каждого месяца в 00:00.
     * Значения по умолчанию: 2 входных двери и 50 межкомнатных дверей на день.
     */
    @Scheduled(cron = "0 0 0 1 * *")
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
    }
}
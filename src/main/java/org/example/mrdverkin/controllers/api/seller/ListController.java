package org.example.mrdverkin.controllers.api.seller;

import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dto.OrderAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/list")
public class ListController {
    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/sellerList")
    public ResponseEntity<Map<String, Object>> sellerList(@AuthenticationPrincipal User user,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {

        try {
            // Создаём пагинацию
            Pageable pageable = PageRequest.of(page, size);

            // Получаем список заказов для пользователя
            Page<Order> ordersPage = orderRepository.findOrdersByUser(user, pageable);

            // Преобразуем заказы в формат OrderAttribute (можно изменить в зависимости от нужд)
            List<OrderAttribute> orderAttributes = OrderAttribute.fromOrderList(ordersPage);

            // Формируем ответ
            Map<String, Object> response = new HashMap<>();
            response.put("orders", orderAttributes);
            response.put("currentPage", page);
            response.put("totalPages", ordersPage.getTotalPages());

            // Возвращаем JSON-ответ
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Error while fetching list"));
        }
    }

}

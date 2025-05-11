package org.example.mrdverkin.controllers.api.orders;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dto.OrderAttribute;
import org.example.mrdverkin.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/list")
@Tag(name = "Order Listing API", description = "Получение и сортировка заказов с пагинацией")
public class ListController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    @Operation(
            summary = "Получить список заказов для продавца",
            description = "Возвращает постраничный список заказов, созданных текущим продавцом.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный ответ"),
                    @ApiResponse(responseCode = "401", description = "Ошибка авторизации", content = @Content)
            }
    )
    @GetMapping("/sellerList")
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

    @Operation(
            summary = "Получить список всех заказов (для админа)",
            description = "Возвращает постраничный список всех заказов в системе.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный ответ")
            }
    )
    @GetMapping("/adminList")
    public ResponseEntity<Map<String, Object>> adminPanel(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderRepository.findAll(pageable);
        List<OrderAttribute> adminMapping = OrderAttribute.fromOrderList(orders);

        response.put("orders", adminMapping);
        response.put("currentPage", page);
        response.put("totalPages", orders.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Отсортировать заказы по продавцу",
            description = "Возвращает заказы, отфильтрованные по продавцу (по нику), с пагинацией.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешный ответ"),
                    @ApiResponse(responseCode = "400", description = "Некорректный запрос", content = @Content)
            }
    )
    @GetMapping("/sort")
    public ResponseEntity<Map<String, Object>> sortOrder(@RequestParam String nickname,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return orderService.searchOrderBySeller(nickname, pageable, page);
    }
}

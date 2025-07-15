package org.example.mrdverkin.controllers.api.orders;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dto.OrderAttribute;
import org.example.mrdverkin.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Order Management API", description = "Удаление, редактирование и получение заказов")
public class ManagementOrder {
    @Autowired
    private OrderService orderService;

    @Operation(
            summary = "Удалить заказ",
            description = "Удаляет заказ по ID, если пользователь имеет на это право.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказ успешно удалён"),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещён", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден", content = @Content)
            }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteOrder(@RequestParam Long id) {
        return orderService.deleteOrderById( id);
    }

    @Operation(
            summary = "Получить заказ для редактирования",
            description = "Возвращает заказ с указанным ID и роль пользователя.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден", content = @Content)
            }
    )
    @GetMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id,
                                                    @AuthenticationPrincipal UserDetails userDetails) {

        Order order = orderService.findOrderById(id);
        OrderAttribute orderAttribute = new OrderAttribute().fromOrder(order);

        Map<String, Object> response = new HashMap<>();
        response.put("orderAttribute", orderAttribute);
        response.put("role",userDetails.getAuthorities());

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Обновить заказ",
            description = "Обновляет информацию о заказе по его ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказ обновлён"),
                    @ApiResponse(responseCode = "400", description = "Ошибка запроса", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден", content = @Content)
            }
    )
    @PatchMapping("/edit/{id}")
    public ResponseEntity<Map<String, Object>> updateOrder(@PathVariable Long id,
                                                           @RequestBody OrderAttribute orderAttribute) {
        return orderService.updateOrder(id, orderAttribute);
    }
}

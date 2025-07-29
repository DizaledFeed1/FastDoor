package org.example.mrdverkin.controllers.api.orders;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.services.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/list")
@Tag(name = "Order Listing API", description = "Получение и сортировка заказов с пагинацией")
public class ListController {

    private final OrderService orderService;

    public ListController(OrderService orderService) {
        this.orderService = orderService;
    }

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
        return orderService.sellerList(user, page, size);
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
        return orderService.adminPanel(page, size);
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
        return orderService.searchOrderBySeller(nickname.toLowerCase(Locale.ROOT), pageable, page);
    }
}

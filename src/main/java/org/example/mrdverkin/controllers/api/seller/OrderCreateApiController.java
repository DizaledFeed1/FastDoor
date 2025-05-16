package org.example.mrdverkin.controllers.api.seller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.DoorLimitsRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dto.DateAvailability;
import org.example.mrdverkin.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order API", description = "Управление созданием заказов и доступностью дат")
public class OrderCreateApiController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SellerService sellerService;

    @Autowired
    private DoorLimitsRepository doorLimitsRepository;

    @Operation(
            summary = "Получить список доступных дат",
            description = "Возвращает список доступных дат для создания заказа.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно", content = @Content(schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "401", description = "Ошибка авторизации", content = @Content)
            }
    )
    @GetMapping("/create")
    public ResponseEntity<Map<String, Object>> getAvailabilityList() {
        try {
            Map<String, Object> response = new HashMap<>();
            List<DateAvailability> availabilityList = DateAvailability.fromDates(doorLimitsRepository,orderRepository);
            response.put("availabilityList", availabilityList);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Error while fetching availabilityList"));
        }
    }

    @Operation(
            summary = "Создать заказ",
            description = "Создаёт новый заказ, проверяет валидность введённых данных.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказ успешно создан", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Неавторизован", content = @Content)
            }
    )
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createOrder(@RequestBody @Valid Order order,
                              @AuthenticationPrincipal User user) {

        return sellerService.create(order,user);
    }
}

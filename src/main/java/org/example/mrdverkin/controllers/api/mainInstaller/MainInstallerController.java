package org.example.mrdverkin.controllers.api.mainInstaller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.mrdverkin.dataBase.Entitys.Condition;
import org.example.mrdverkin.dataBase.Entitys.Installer;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Repository.DoorLimitsRepository;
import org.example.mrdverkin.dataBase.Repository.InstallerRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dto.DateAvailability;
import org.example.mrdverkin.dto.InstallerInfo;
import org.example.mrdverkin.dto.OrderAttribute;
import org.example.mrdverkin.services.BotService;
import org.example.mrdverkin.services.InstallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mainInstaller")
@Tag(name = "Main Installer", description = "API для работы с основным установщиком")
public class MainInstallerController {

    private final OrderRepository orderRepository;
    private final DoorLimitsRepository doorLimitsRepository;
    private final InstallerRepository installerRepository;
    private final BotService botService;
    private final InstallerService installerService;

    public MainInstallerController(OrderRepository orderRepository, DoorLimitsRepository doorLimitsRepository,
                                   InstallerRepository installerRepository, BotService botService, InstallerService installerService) {
        this.orderRepository = orderRepository;
        this.doorLimitsRepository = doorLimitsRepository;
        this.installerRepository = installerRepository;
        this.botService = botService;
        this.installerService = installerService;
    }

    private final String ERROR = "error";

    @Operation(
            summary = "Получить список заказов без установщика с дополнительной информацией",
            description = "Возвращает страницы заказов без установщика, список установщиков и доступные даты",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные успешно получены"),
                    @ApiResponse(responseCode = "400", description = "Неверные параметры страницы или размера", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<Map<String, Object>> mainInstaller(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        try {
            if (page < 0 || size <= 0 || size > 100) {
                return ResponseEntity.badRequest().body(Map.of(ERROR, "Неверные параметры страницы или размера"));
            }

            Pageable pageable = PageRequest.of(page, size);
            Page<Order> ordersPage = orderRepository.findByInstallerNull(pageable, Condition.DELETED);
            List<DateAvailability> availabilityList = DateAvailability.fromDates(doorLimitsRepository, orderRepository);
            List<OrderAttribute> orderAttributes = OrderAttribute.fromOrderList(ordersPage);

            Map<String, Object> response = new HashMap<>();
            response.put("orders", orderAttributes);
            response.put("installers", installerRepository.findAll());
            response.put("availabilityList", availabilityList);
            response.put("currentPage", page);
            response.put("totalPages", ordersPage.getTotalPages());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put(ERROR, "Внутренняя ошибка сервера");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(
            summary = "Получить постраничный список заказов без установщика",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказы успешно получены"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
            }
    )
    @GetMapping("/pageable")
    public ResponseEntity<Map<String, Object>> pageableOrders(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int size){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Order> ordersPage = orderRepository.findByInstallerNull(pageable, Condition.DELETED);
            List<OrderAttribute> orderAttributes = OrderAttribute.fromOrderList(ordersPage);

            Map<String, Object> response = new HashMap<>();
            response.put("orders", orderAttributes);
            response.put("currentPage", page);
            response.put("totalPages", ordersPage.getTotalPages());

            return ResponseEntity.ok(response);

        } catch (Exception e){

            Map<String, Object> error = new HashMap<>();
            error.put(ERROR, "Внутренняя ошибка сервера");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @Operation(
            summary = "Выбрать установщика для заказа или измениние заказа",
            description = "Обновляет заказ, устанавливая выбранного установщика и комментарий",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Установщик успешно выбран"),
                    @ApiResponse(responseCode = "500", description = "Ошибка выбора установщика", content = @Content)
            }
    )
    @PostMapping()
    public ResponseEntity<Map<String, Object>> selectInstaller(@RequestBody InstallerInfo installerInfo) {
        installerService.selectInstaller(installerInfo, orderRepository, botService);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получить список всех заказов с пагинацией",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказы успешно получены")
            }
    )
    @GetMapping("/listOrdersMainInstaller")
    public ResponseEntity<Map<String, Object>> listOrders(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> ordersPage = orderRepository.findAll(pageable, Condition.DELETED);
        List<OrderAttribute> orderAttributes = OrderAttribute.fromOrderList(ordersPage);

        Map<String, Object> response = new HashMap<>();
        response.put("orders", orderAttributes);
        response.put("currentPage", page);
        response.put("totalPages", ordersPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleInvalidEnum(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}

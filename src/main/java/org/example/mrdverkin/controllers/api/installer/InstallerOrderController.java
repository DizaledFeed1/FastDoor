package org.example.mrdverkin.controllers.api.installer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dto.installer.OrderApproveResponseDto;
import org.example.mrdverkin.dto.installer.OrderCancelRequestDto;
import org.example.mrdverkin.dto.installer.OrderCancelResponseDto;
import org.example.mrdverkin.services.InstallerOrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/installer")
@Schema(description = "Сервис работы с заказами")
public class InstallerOrderController {

    private final InstallerOrderService orderService;

    @PostMapping("/order/approde/{orderId}")
    @Operation(summary = "Подтверждение заказа")
    public OrderApproveResponseDto approveOrder(@PathVariable("orderId") UUID orderId,
                                                @AuthenticationPrincipal User user) {
        return orderService.approveOrder(orderId, user);
    }

    @PostMapping("/order/cancel/{orderId}")
    @Operation(summary = "Отказ от заказа")
    public OrderCancelResponseDto canceledOrder(@PathVariable("orderId") UUID orderId,
                                                              @RequestBody @Valid OrderCancelRequestDto request,
                                                              @AuthenticationPrincipal User user) {
        return orderService.cancelOrder(orderId, user, request);
    }
}

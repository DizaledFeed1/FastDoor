package org.example.mrdverkin.controllers.api.installer;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.example.mrdverkin.dataBase.Entitys.Condition;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dto.installer.InstallerOrderResponseDto;
import org.example.mrdverkin.services.InstallerListService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@AllArgsConstructor
@RequestMapping("/api/list")
public class InstallerListController {

    private final InstallerListService service;

    @GetMapping("/installer/orders")
    @Operation(summary = "Получение списка заказов для установщика")
    public Page<InstallerOrderResponseDto> getOrders(@RequestParam List<Condition> statuses,
                                                           @AuthenticationPrincipal User user,
                                                           Pageable pageable) {
        return service.getSortedOrders(statuses,user, pageable);
    }
}

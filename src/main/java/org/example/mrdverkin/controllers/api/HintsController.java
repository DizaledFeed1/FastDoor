package org.example.mrdverkin.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hints")
@Tag(name = "Контроллер подсказок", description = "Контроллер реализует получение статуса подсказок пользователя " +
        "и замены статуса")
public class HintsController {
    private final UserService userService;

    public HintsController(UserService userService) {
        this.userService = userService;
    }

    @Operation(description = "Возвращает true - если у пользователя включены подсказки и fase - если иначе")
    @GetMapping()
    public boolean getHints(@AuthenticationPrincipal User user) {
        return user.isHints();
    }

    @Operation(description = "Ревёрсит значение подсказки с true на false и наоборот")
    @ApiResponse (responseCode = "200", description = "Возвращает новое значение true/false")
    @PatchMapping("/reverse")
    public boolean reverseHints(@AuthenticationPrincipal User user) {
        userService.hintsRevers(user);
        return user.isHints();
    }
}

package org.example.mrdverkin.controllers.api.mainInstaller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.mrdverkin.dto.InstallerInfo;
import org.example.mrdverkin.services.InstallerService;
import org.example.mrdverkin.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/listInstallers")
@Tag(name = "Installers", description = "Управление установщиками")
public class ListInstallerController {

    @Autowired
    private InstallerService installerService;
    @Autowired
    private OrderService orderService;

    @Operation(summary = "Получить список всех установщиков",
            description = "Возвращает список всех установщиков в системе")
    @ApiResponse(responseCode = "200", description = "Список установщиков успешно получен")
    @GetMapping
    public ResponseEntity<?> listInstallers() {

        Map<String, Object> response = new HashMap<>();
        response.put("installers",installerService.getAllInstallers());

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Удалить установщика по ID",
            description = "Удаляет установщика по указанному идентификатору")
    @ApiResponse(responseCode = "200", description = "Установщик успешно удалён")
    @ApiResponse(responseCode = "404", description = "Установщик с таким ID не найден", content = @Content)
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteInstaller(@PathVariable Long id) {
        try {
            installerService.deleteInstallerById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Внутренняя ошибка сервера");
        }
    }

    @Operation(summary = "Создать нового установщика",
            description = "Создаёт нового установщика с указанными полным именем и телефоном")
    @ApiResponse(responseCode = "200", description = "Установщик успешно создан")
    @ApiResponse(responseCode = "400", description = "Неверные данные для создания установщика", content = @Content)
    @PostMapping("/create")
    public ResponseEntity<?> createInstaller(@RequestParam String fullName, @RequestParam String phone) {
        try {
            installerService.createInstaller(fullName, phone);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Неверные данные!");
        }
    }

    @Operation(summary = "Получить количество дверей назначеных установщику на определённую дату",
            description = "Возвращает количество дверей для установщиков")
    @ApiResponse(responseCode = "200", description = "Данные",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = InstallerInfo.class))))
    @GetMapping("/workload")
    public ResponseEntity<?> getWorkload(@RequestParam Date date) {
        return installerService.getWorkloadDate(date);
    }
}

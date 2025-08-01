package org.example.mrdverkin.controllers.api.installer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.mrdverkin.dataBase.Entitys.Installer;
import org.example.mrdverkin.services.InstallerService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/installer")
@Tag(name = "InstallersEdit", description = "Изменение установщиков")
public class InstallerController {

    private final InstallerService installerService;

    public InstallerController(InstallerService installerService) {
        this.installerService = installerService;
    }

    @Operation(summary = "Получить установщика по id",
            description = "Возвращает выбранного установщика для edit")
    @ApiResponse(responseCode = "200", description = "Данные установщика")
    @ApiResponse(responseCode = "404", description = "Установщик не найден")
    @GetMapping("/{id}")
    public ResponseEntity<Installer> installer(@PathVariable Long id) {
        Installer installer = installerService.findInstallerById(id);
        if (installer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(installer);
    }

    @Operation(summary = "Обновить данные установщика",
            description = "Обновляет данные выбранного установщика")
    @ApiResponse(responseCode = "200", description = "Данные обновлены")
    @ApiResponse(responseCode = "404", description = "Установщик не найден")
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateInstaller(@PathVariable Long id, @RequestParam String fullName, @RequestParam String phone) {
        return installerService.updateInstaller(id, fullName, phone);
    }
}

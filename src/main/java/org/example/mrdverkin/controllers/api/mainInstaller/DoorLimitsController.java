package org.example.mrdverkin.controllers.api.mainInstaller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.mrdverkin.dataBase.Entitys.DoorLimits;
import org.example.mrdverkin.dataBase.Repository.DoorLimitsRepository;
import org.example.mrdverkin.dto.DateAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/doorLimits")
@Tag(name = "Door Limits", description = "API для работы с датой установки")
public class DoorLimitsController {
    @Autowired
    private DoorLimitsRepository doorLimitsRepository;

    @Operation(
            summary = "Закрыть день для добавления заказов",
            description = "Указывать нужно только date всё остальное мусор",
            parameters = {
                    @Parameter(name = "date", description = "Дата которую нужно закрыть", example = "2025-06-06")
            }
    )
    @PatchMapping("/closeDate")
    public ResponseEntity<String> closeDate(@RequestBody DateAvailability dateAvailability) {
        try {
            doorLimitsRepository.closeDate(Date.valueOf(dateAvailability.getDate()));
            return ResponseEntity.ok().body("Дата закрыта!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка данных!");
        }
    }

    @Operation(
            summary = "Открыть день для добавления заказов",
            description = "Указывать нужно только date всё остальное мусор",
            parameters = {
                    @Parameter(name = "date", description = "Дата которую нужно закрыть", example = "2025-06-06")
            }
    )
    @PatchMapping("/openDate")
    public ResponseEntity<String> openDate(@RequestBody DateAvailability dateAvailability) {
        try {
            doorLimitsRepository.openDate(Date.valueOf(dateAvailability.getDate()));
            return ResponseEntity.ok().body("Дата открыта!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка данных!");
        }
    }

    @Operation(
            summary = "Изменить ограничения дня",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные успешно добавлены"),
                    @ApiResponse(responseCode = "404", content = @Content)
            }
    )
    @PatchMapping("/editDate")
    public ResponseEntity<String> editDate(@RequestBody DateAvailability dateAvailability) {
        DoorLimits doorLimits = doorLimitsRepository.findByLimitDate(Date.valueOf(dateAvailability.getDate()));
        if (doorLimits == null) {
            return ResponseEntity.notFound().build();
        }
        doorLimits.setInDoorQuantity(Math.toIntExact(dateAvailability.getInDoorQuantity()));
        doorLimits.setFrontDoorQuantity(Math.toIntExact(dateAvailability.getFrontDoorQuantity()));
        System.out.println(dateAvailability.isAvailable());
        doorLimits.setAvailability(dateAvailability.isAvailable());
        doorLimitsRepository.save(doorLimits);

        return ResponseEntity.ok().body("Значение обновлено!");
    }

    @Operation(
            summary = "Получить все дни календаря",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные календаря"),
            }
    )
    @GetMapping("/allDays")
    public ResponseEntity<List<DoorLimits>> allDays() {
        return ResponseEntity.ok().body(doorLimitsRepository.findAllByNoLimit());
    }
}

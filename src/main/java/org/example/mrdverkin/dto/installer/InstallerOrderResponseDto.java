package org.example.mrdverkin.dto.installer;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallerOrderResponseDto {

    @Schema(description = "id заказа")
    private UUID id;

    @Schema(description = "Адрес доставки")
    private String address;

    @Schema(description = "Номер телефона покупателя")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Schema(description = "Кол-во входных дверей")
    @JsonProperty("front_door_count")
    private Integer frontDoorCount;

    @Schema(description = "Кол-во межкомнатных дверей")
    @JsonProperty("in_door_count")
    private Integer inDoorCount;

    @Schema(description = "Комментарий магазина")
    @JsonProperty("message_seller")
    private String messageSeller;

    @Schema(description = "Комментарий главного установщика")
    @JsonProperty("message_main_installer")
    private String messageMainInstaller;

    @Schema(description = "Дата установки")
    @JsonProperty("order_date")
    private LocalDate orderDate;

    @Schema(description = "ФИО покупателя")
    @JsonProperty("full_name")
    private String fullName;
}

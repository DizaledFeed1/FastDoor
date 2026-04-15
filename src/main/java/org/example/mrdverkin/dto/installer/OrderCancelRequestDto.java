package org.example.mrdverkin.dto.installer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Valid
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос сервиса отмены заказа установщиком")
public class OrderCancelRequestDto {

    @NotBlank
    @Size(min = 5, max = 2000)
    @Schema(description = "Пояснение отмены заказ")
    String comment;
}

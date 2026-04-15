package org.example.mrdverkin.dto.installer;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.mrdverkin.dataBase.Entitys.Condition;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ сервиса отмены заказа")
public class OrderCancelResponseDto {

    @JsonProperty("order_id")
    @Schema(description = "Id заказа")
    private UUID orderId;

    @JsonProperty("status_order")
    @Schema(description = "Статус заказа")
    private Condition statusOrder;
}

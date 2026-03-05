package org.example.mrdverkin.dto.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Value
@Builder
public class SmsRequest {

    @Schema(
            description = "Номер телефона в формате E.164, на который будет отправлено сообщение",
            example = "+79137488501"
    )
    @NotBlank
    @JsonProperty("phone_number")
    String phoneNumber;

    /**
     * Код который отправляем пользователю
     */
    @NotBlank
    @Schema(
            description = "Код, который отправляем пользователю (если нужно задать свой)",
            example = "123456"
    )
    @Min(4)
    @Max(8)
    String code;
}

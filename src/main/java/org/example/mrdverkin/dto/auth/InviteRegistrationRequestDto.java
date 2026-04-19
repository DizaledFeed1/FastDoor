package org.example.mrdverkin.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Запрос валидации хэша")
public class InviteRegistrationRequestDto {

    @NotBlank
    @Size(min = 64, max = 64)
    @JsonProperty("invite_code")
    @Schema(description = "Пригласительный код  юзера")
    private String inviteCode;
}

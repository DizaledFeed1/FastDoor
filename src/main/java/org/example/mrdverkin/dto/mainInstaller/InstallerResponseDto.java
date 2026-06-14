package org.example.mrdverkin.dto.mainInstaller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@Schema(description = "Ответ сервиса получения списка установщиков")
public class InstallerResponseDto {

    @Schema(description = "Id")
    private UUID id;

    @JsonProperty("full_name")
    @Schema(description = "ФИО установщика")
    private String fullName;

    @Schema(description = "Номер телефона")
    private String phone;

    @JsonProperty("invite_code")
    @Schema(description = "Код приглашения")
    private String inviteCode;
}

package org.example.mrdverkin.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.example.mrdverkin.dataBase.Entitys.Role;

@Data
@Builder
@Schema(description = "Ответ валидации хэша")
public class InviteRegistrationResponseDto {

    @Schema(description = "Название магазина/ФИО юзера")
    private String nickname;

    @Schema(description = "Роль пользователя")
    private Role role;
}

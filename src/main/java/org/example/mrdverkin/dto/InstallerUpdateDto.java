package org.example.mrdverkin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallerUpdateDto {
    private Long id;
    private String fullName;
    private String phone;

    /**
     * Id установщика в ТГ
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String TgId;

    /**
     * Id установщика в Максе
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String MaxId;
}

package org.example.mrdverkin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.sql.Date;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoorLimitResponseDto {
    private Long id;
    private Date limitDate;
    private int frontDoorQuantity;
    private int inDoorQuantity;
    private boolean availability;
}

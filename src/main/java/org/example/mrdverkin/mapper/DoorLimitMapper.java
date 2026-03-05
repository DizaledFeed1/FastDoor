package org.example.mrdverkin.mapper;

import org.example.mrdverkin.dataBase.Entitys.DoorLimits;
import org.example.mrdverkin.dto.DoorLimitResponseDto;

public class DoorLimitMapper {

    public static DoorLimitResponseDto entityToResponseDto(DoorLimits doorLimits) {
        return DoorLimitResponseDto.builder()
                .id(doorLimits.getId())
                .limitDate(doorLimits.getLimitDate())
                .frontDoorQuantity(doorLimits.getFrontDoorQuantity())
                .inDoorQuantity(doorLimits.getInDoorQuantity())
                .availability(doorLimits.getAvailability())
                .build();
    }
}

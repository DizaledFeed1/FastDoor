package org.example.mrdverkin.services;

import org.example.mrdverkin.dataBase.Repository.DoorLimitsRepository;
import org.example.mrdverkin.dto.DoorLimitResponseDto;
import org.example.mrdverkin.mapper.DoorLimitMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoorLimitsService {

    private final DoorLimitsRepository doorLimitsRepository;

    public DoorLimitsService(DoorLimitsRepository doorLimitsRepository) {
        this.doorLimitsRepository = doorLimitsRepository;
    }

    public Page<DoorLimitResponseDto> allDays(Pageable pageable) {
        return doorLimitsRepository.findAll(pageable).map(DoorLimitMapper::entityToResponseDto);
    }
}
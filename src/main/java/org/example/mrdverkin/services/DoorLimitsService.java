package org.example.mrdverkin.services;

import org.example.mrdverkin.dataBase.Entitys.DoorLimits;
import org.example.mrdverkin.dataBase.Repository.DoorLimitsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoorLimitsService {

    private final DoorLimitsRepository doorLimitsRepository;

    public DoorLimitsService(DoorLimitsRepository doorLimitsRepository) {
        this.doorLimitsRepository = doorLimitsRepository;
    }

    public ResponseEntity<List<String>> allDays() {
        List<DoorLimits> doorLimits = doorLimitsRepository.findAllByNoLimit();

        List<String> response = new ArrayList<>();
        for (DoorLimits doorLimit: doorLimits) {
            response.add(doorLimit.getLimitDate().toString());
        }
        return ResponseEntity.ok().body(response);
    }
}

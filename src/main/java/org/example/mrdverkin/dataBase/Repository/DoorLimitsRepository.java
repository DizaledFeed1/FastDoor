package org.example.mrdverkin.dataBase.Repository;

import org.example.mrdverkin.dataBase.Entitys.DoorLimits;
import org.example.mrdverkin.dto.DateAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.time.LocalDate;

public interface DoorLimitsRepository extends JpaRepository<DoorLimits, Long> {
    DoorLimits findByLimitDate(Date limitDate);

}

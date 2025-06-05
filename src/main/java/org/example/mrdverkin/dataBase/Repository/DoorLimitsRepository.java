package org.example.mrdverkin.dataBase.Repository;

import org.example.mrdverkin.dataBase.Entitys.DoorLimits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Date;


public interface DoorLimitsRepository extends JpaRepository<DoorLimits, Long> {
    DoorLimits findByLimitDate(Date limitDate);

    @Modifying
    @Transactional
    @Query("UPDATE DoorLimits d SET d.availability = false WHERE d.limitDate = :date")
    void closeDate(@Param("date") Date data);
}

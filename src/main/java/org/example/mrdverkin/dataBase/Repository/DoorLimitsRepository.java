package org.example.mrdverkin.dataBase.Repository;

import org.example.mrdverkin.dataBase.Entitys.DoorLimits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Date;
import java.util.List;


public interface DoorLimitsRepository extends JpaRepository<DoorLimits, Long> {
    DoorLimits findByLimitDate(Date limitDate);

    @Query("SELECT d FROM DoorLimits d WHERE d.limitDate > CURRENT_TIMESTAMP ORDER BY d.limitDate ASC")
    List<DoorLimits> findAll();

    @Modifying
    @Transactional
    @Query("UPDATE DoorLimits d SET d.availability = false WHERE d.limitDate = :date")
    void closeDate(@Param("date") Date data);

    @Modifying
    @Transactional
    @Query("UPDATE DoorLimits d SET d.availability = true WHERE d.limitDate = :date")
    void openDate(@Param("date") Date data);
}

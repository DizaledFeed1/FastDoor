package org.example.mrdverkin.dataBase.Repository;

import org.example.mrdverkin.dataBase.Entitys.Condition;
import org.example.mrdverkin.dataBase.Entitys.Installer;
import org.example.mrdverkin.dto.InstallerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface InstallerRepository extends JpaRepository<Installer, Long> {
    @Query(value = "SELECT i FROM Installer i where i.fullName = :fullSelectName")
    Installer findByName(@Param("fullSelectName")String name);

    Installer findInstallersById(Long id);

    @Query(value = "SELECT " +
            "i.id, " +
            "i.full_name, " +
            "COALESCE(SUM(CASE WHEN o.date_order = :dateOrder THEN o.frontdoorquantity ELSE 0 END), 0) AS frontdoorquantitysum, " +
            "COALESCE(SUM(CASE WHEN o.date_order = :dateOrder THEN o.indoorquantity ELSE 0 END), 0) AS indoorquantitysum " +
            "FROM installer i " +
            "LEFT JOIN \"order\" o ON i.id = o.installer_id != :condition " +
            "WHERE o.condition != " +
            "GROUP BY i.id, i.full_name",
            nativeQuery = true)
    List<InstallerInfo> searchDoorbyDate(@Param("dateOrder") Date dateOrder,
                                         @Param("condition") Condition condition);

}

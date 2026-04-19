package org.example.mrdverkin.dataBase.Repository;

import org.example.mrdverkin.dataBase.Entitys.Condition;
import org.example.mrdverkin.dataBase.Entitys.Installer;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dto.InstallerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InstallerRepository extends JpaRepository<Installer, UUID> {
    @Query(value = "SELECT i FROM Installer i where i.fullName = :fullSelectName")
    Installer findByName(@Param("fullSelectName")String name);

    Installer findInstallersById(UUID id);

    @Query("SELECT NEW org.example.mrdverkin.dto.InstallerInfo(" +
            "i.id, " +
            "i.fullName, " +
            "CAST(COALESCE(SUM(CASE WHEN o.dateOrder = :dateOrder AND o.condition <> :condition THEN o.frontDoorQuantity ELSE 0 END), 0) AS Long), " +
            "CAST(COALESCE(SUM(CASE WHEN o.dateOrder = :dateOrder AND o.condition <> :condition THEN o.inDoorQuantity ELSE 0 END), 0) AS Long)) " +
            "FROM Installer i " +
            "LEFT JOIN i.orders o " +
            "GROUP BY i.id, i.fullName")
    List<InstallerInfo> searchDoorbyDate(@Param("dateOrder") LocalDate dateOrder,
                                         @Param("condition") Condition condition);

    Optional<Installer> findByPhone(String phone);

    Installer findByUser(User user);
}
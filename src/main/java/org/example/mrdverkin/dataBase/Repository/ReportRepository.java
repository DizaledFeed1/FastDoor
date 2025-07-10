package org.example.mrdverkin.dataBase.Repository;

import org.example.mrdverkin.dataBase.Entitys.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT r " +
            "FROM report r " +
            "where r.owner.id = :user_id " +
            "order by r.dateCreated desc ")
    List<Report> findAllByOwner(@Param("user_id") Long userId);
}

package org.example.mrdverkin.dataBase.Repository;

import org.example.mrdverkin.dataBase.Entitys.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT r " +
            "FROM Report r " +
            "where r.owner.id = :user_id " +
            "order by r.dateCreated desc ")
    List<Report> findAllByOwner(@Param("user_id") Long userId);

    @Query("SELECT r FROM Report r LEFT JOIN FETCH r.orders WHERE r.id = :id")
    Optional<Report> findByIdWithOrders(@Param("id") Long id);


}

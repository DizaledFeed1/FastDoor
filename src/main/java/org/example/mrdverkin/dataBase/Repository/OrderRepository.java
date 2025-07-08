package org.example.mrdverkin.dataBase.Repository;

import org.example.mrdverkin.dataBase.Entitys.Condition;
import org.example.mrdverkin.dataBase.Entitys.Installer;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dto.DateAvailability;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o where o.condition != :condition ORDER BY o.placeAt DESC")
    Page<Order> findAll(Pageable pageable, @Param("condition") Condition condition);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Order o set o.installer = :newInstaller where o.id = :orderId")
    void updateInstaller(@Param("newInstaller") Installer installer, @Param("orderId") Long orderId);

    @Query(value = "SELECT o FROM Order o WHERE o.installer IS null AND o.condition != :condition")
    Page<Order> findByInstallerNull(Pageable pageable, @Param("condition") Condition condition);

    @Query(value = "SELECT o FROM Order o WHERE o.user = :actualUser " +
            "ORDER BY o.placeAt DESC")
    Page<Order> findOrdersByUserAll(@Param("actualUser") User user, Pageable pageable);


    @Query(value = "SELECT o FROM Order o WHERE o.user = :actualUser AND o.condition != :condition " +
            "ORDER BY o.placeAt DESC")
    Page<Order> findOrdersByUser(@Param("actualUser") User user, @Param("condition") Condition condition, Pageable pageable);

    @Query(value = "select O from Order O where O.id = :orderid")
    Order findByOrderId(@Param("orderid")Long orderId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Order o set o.messageMainInstaller = :commenet where o.id = :orderId")
    void updateComment(@Param("orderId") Long id, @Param("commenet")String comment);

    @Query("SELECT new org.example.mrdverkin.dto.DateAvailability(" +
            "o.dateOrder, " +
            "CAST(COALESCE(SUM(o.frontDoorQuantity), 0) AS long), " +
            "CAST(COALESCE(SUM(o.inDoorQuantity), 0) AS long), " +
            "o.doorLimits.availability) " +
            "FROM Order o WHERE o.dateOrder = :date " +
            "GROUP BY o.dateOrder, o.doorLimits.availability")
    DateAvailability getDoorCountsByDate(@Param("date") LocalDate date);

    @Query("""
    SELECT new org.example.mrdverkin.dto.DateAvailability(
        o.dateOrder,
        CAST(COALESCE(SUM(o.frontDoorQuantity), 0) AS long),
        CAST(COALESCE(SUM(o.inDoorQuantity), 0) AS long),
        o.doorLimits.availability)
    FROM Order o
    WHERE o.condition != :condition
    GROUP BY o.dateOrder, o.doorLimits.availability
    """)
    List<DateAvailability> getDoorCountsGroupedByDate(@Param("condition")  Condition condition);



}

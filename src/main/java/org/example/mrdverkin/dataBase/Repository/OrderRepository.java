package org.example.mrdverkin.dataBase.Repository;

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
    Page<Order> findAll(Pageable pageable);

//    @Query("SELECT 70 - COALESCE(SUM(o.inDoorQuantity), 0) FROM Order o WHERE o.dateOrder = :data")
//    int numberOfInDoorsToInstallation(@Param("data") LocalDate data);
//
//    @Query("SELECT 2 - COALESCE(SUM(o.frontDoorQuantity), 0) FROM Order o WHERE o.dateOrder = :data")
//    int numberOfFrontDoorsToInstallation(@Param("data") LocalDate data);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Order o set o.installer = :newInstaller where o.id = :orderId")
    void updateInstaller(@Param("newInstaller") Installer installer, @Param("orderId") Long orderId);

    @Query(value = "SELECT o FROM Order o WHERE o.installer IS null")
    Page<Order> findByInstallerNull(Pageable pageable);

    @Query(value = "SELECT o FROM Order o WHERE o.user = :actualUser")
    Page<Order> findOrdersByUser(@Param("actualUser") User user, Pageable pageable);

    @Query(value = "select O from Order O where O.id = :orderid")
    Order findByOrderId(@Param("orderid")Long orderId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Order o set o.messageMainInstaller = :commenet where o.id = :orderId")
    void updateComment(@Param("orderId") Long id, @Param("commenet")String comment);

    @Query("SELECT new org.example.mrdverkin.dto.DateAvailability(" +
            "o.dateOrder, " +
            "CAST(COALESCE(SUM(o.frontDoorQuantity), 0) AS long), " +
            "CAST(COALESCE(SUM(o.inDoorQuantity), 0) AS long)) " +
            "FROM Order o WHERE o.dateOrder = :date " +
            "GROUP BY o.dateOrder")
    DateAvailability getDoorCountsByDate(@Param("date") LocalDate date);


}

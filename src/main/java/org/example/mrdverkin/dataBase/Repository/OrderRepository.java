package org.example.mrdverkin.dataBase.Repository;

import org.example.mrdverkin.dataBase.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
//    @Query(value = "SELECT * FROM \"order\" o WHERE o.date_order BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '7' DAY", nativeQuery = true)
//    List<Order> findOrdersOlderThan7Days();

}

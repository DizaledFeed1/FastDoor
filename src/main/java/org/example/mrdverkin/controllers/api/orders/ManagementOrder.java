package org.example.mrdverkin.controllers.api.orders;

import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dto.OrderAttribute;
import org.example.mrdverkin.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ManagementOrder {
    @Autowired
    private OrderService orderService;

    /**
     * Медот для удаления заказа по id
     * @param user
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteOrder(@AuthenticationPrincipal User user,
                                                           @RequestParam Long id) {
        return orderService.deleteOrderById(user, id);
    }

    /**
     * Метод получения заказа для изменения.
     * @param id
     * @param userDetails
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> edit(@PathVariable Long id,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(userDetails.getAuthorities());
        Order order = orderService.findOrderById(id);
        OrderAttribute orderAttribute = new OrderAttribute().fromOrder(order);

        Map<String, Object> response = new HashMap<>();
        response.put("orderAttribute", orderAttribute);
        response.put("role",userDetails.getAuthorities());

        return ResponseEntity.ok(response);
    }
}

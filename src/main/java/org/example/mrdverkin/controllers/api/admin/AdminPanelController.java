//package org.example.mrdverkin.controllers.api.admin;
//
//import org.example.mrdverkin.dataBase.Entitys.Order;
//import org.example.mrdverkin.dataBase.Repository.OrderRepository;
//import org.example.mrdverkin.dto.OrderAttribute;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/list")
//public class AdminPanelController {
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @GetMapping
//    public ResponseEntity<Map<String, Object>> adminPanel(@RequestParam(defaultValue = "0") int page,
//                                                          @RequestParam(defaultValue = "10") int size) {
//        Map<String, Object> response = new HashMap<>();
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Order> orders = orderRepository.findAll(pageable);
//        List<OrderAttribute> adminMapping = OrderAttribute.fromOrderList(orders);
//
//        response.put("orders", adminMapping);
//        response.put("currentPage", page);
//        response.put("totalPages", orders.getTotalPages());
//
//        return ResponseEntity.ok(response);
//    }
//}

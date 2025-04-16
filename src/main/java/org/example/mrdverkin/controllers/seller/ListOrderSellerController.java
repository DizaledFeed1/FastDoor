//package org.example.mrdverkin.controllers.seller;
//
//import org.example.mrdverkin.dataBase.Entitys.User;
//import org.example.mrdverkin.dto.OrderAttribute;
//import org.example.mrdverkin.dataBase.Entitys.Order;
//import org.example.mrdverkin.dataBase.Repository.OrderRepository;
//import org.example.mrdverkin.services.OrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/listOrdersSeller")
//public class ListOrderSellerController {
//    @Autowired
//    private OrderRepository orderRepository;
//    @Autowired
//    private OrderService orderService;
//
//    @GetMapping
//    public String listOrders(@AuthenticationPrincipal User user,Model model,
//                             @RequestParam(defaultValue = "0") int page,
//                             @RequestParam(defaultValue = "10") int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Order> ordersPage = orderRepository.findOrdersByUser(user,pageable);
//        List<OrderAttribute> orderAttributes = OrderAttribute.fromOrderList(ordersPage);
//        model.addAttribute("orders", orderAttributes);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", ordersPage.getTotalPages());
//        return "listOrdersSeller";
//    }
//
//    @PostMapping("/delete/{id}")
//    public String deleteOrder(@PathVariable Long id) {
//        orderService.deleteOrderById(id);
//        return "redirect:/listOrdersSeller";
//    }
//}

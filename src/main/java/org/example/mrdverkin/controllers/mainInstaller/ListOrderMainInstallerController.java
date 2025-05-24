//package org.example.mrdverkin.controllers.mainInstaller;
//
//import org.example.mrdverkin.dataBase.Entitys.Order;
//import org.example.mrdverkin.dto.OrderAttribute;
//import org.example.mrdverkin.dataBase.Repository.OrderRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/home/mainInstaller/listOrdersMainInstaller")
//public class ListOrderMainInstallerController {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @GetMapping
//    public String listOrders(Model model,
//                             @RequestParam(defaultValue = "0") int page,
//                             @RequestParam(defaultValue = "10") int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Order> ordersPage = orderRepository.findAll(pageable);
//        List<OrderAttribute> orderAttributes = OrderAttribute.fromOrderList(ordersPage);
//
//        model.addAttribute("orders", orderAttributes);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", ordersPage.getTotalPages());
//        return "listOrdersMainInstaller";
//    }
//}

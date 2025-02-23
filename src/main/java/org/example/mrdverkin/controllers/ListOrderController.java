package org.example.mrdverkin.controllers;

import org.example.mrdverkin.dataBase.Order;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/listOrders")
public class ListOrderController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public String listOrders(Model model) {
        List<Order> ordes = orderRepository.findAll();
        model.addAttribute("orders", ordes);
        return "listOrders";
    }
}

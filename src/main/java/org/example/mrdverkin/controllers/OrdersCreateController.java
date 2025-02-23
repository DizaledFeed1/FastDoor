package org.example.mrdverkin.controllers;

import jakarta.validation.Valid;
import org.example.mrdverkin.dataBase.*;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/orders")
public class OrdersCreateController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/create")
    public String createOrder(Model model) {
        return "create";
    }
    @ModelAttribute("order")
    public Order order() {
        return new Order();
    }

    @PostMapping
    public String createOrder(@Valid @ModelAttribute Order order,
                              Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "create";
        }
        orderRepository.save(order);
        sessionStatus.setComplete();
        return "done";
    }
}

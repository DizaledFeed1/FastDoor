package org.example.mrdverkin.controllers;

import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/home")
    public String home(Model model) {
//        List<Order> orders = orderRepository.findOrdersOlderThan7Days();
//        model.addAttribute("orders", orders);
        return "home";
    }

    public String formatPhoneNumber(String phone) {
        if (phone != null && phone.length() == 11) {
            return phone.substring(0, 1) + "-" + phone.substring(1, 4) + "-" + phone.substring(4, 7) + "-" + phone.substring(7, 9) + "-" + phone.substring(9, 11);
        }
        return phone;
    }

}

//package org.example.mrdverkin.controllers.seller;
//
//import jakarta.validation.Valid;
//import org.example.mrdverkin.dataBase.Entitys.Order;
//import org.example.mrdverkin.dataBase.Entitys.User;
//import org.example.mrdverkin.dataBase.Repository.OrderRepository;
//import org.example.mrdverkin.dataBase.Repository.UserRepository;
//import org.example.mrdverkin.dto.DateAvailability;
//import org.example.mrdverkin.services.SellerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.support.SessionStatus;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/orders")
///**
// * Класс котроллер для создания заказов.
// * @author Кирилл Селявский
// * @version 1.0
// */
//public class OrdersCreateController {
//    @Autowired
//    private OrderRepository orderRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private SellerService sellerService;
//
//    /**
//     * Метод для отображен тэмплейта.
//     * @param model
//     * @return
//     */
//    @GetMapping("/create")
//    public String createOrder(Model model) {
//        List<DateAvailability> availabilityList = DateAvailability.fromDates(orderRepository);
//        model.addAttribute("availabilityList", availabilityList);
//        return "create";
//    }
//
//    /**
//     * Метод возвращает модель "order" для создания заказа.
//     * @return
//     */
//    @ModelAttribute("order")
//    public Order order() {
//        return new Order();
//    }
//
//    /**
//     * Метод для создания заказа, проверяет корректность введённых данных.
//     * @param order
//     * @param user
//     * @param sessionStatus
//     * @param bindingResult
//     * @return
//     */
//    @PostMapping
//    public String createOrder(@Valid @ModelAttribute Order order,
//                              @AuthenticationPrincipal User user,
//                              SessionStatus sessionStatus, BindingResult bindingResult) {
//        sellerService.check(bindingResult,order);
//
//        if (bindingResult.hasErrors()) {
//            return "create";
//        }
//            order.setUser(user);
//            orderRepository.save(order);
//            userRepository.save(user);
//            sessionStatus.setComplete();
//
//        return "done";
//    }
//}

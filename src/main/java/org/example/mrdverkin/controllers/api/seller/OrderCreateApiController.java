package org.example.mrdverkin.controllers.api.seller;

import jakarta.validation.Valid;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dto.DateAvailability;
import org.example.mrdverkin.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderCreateApiController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private UserRepository userRepository;

    /**
     * Метод для отображен тэмплейта.
          * @return
     */
    @GetMapping("/create")
    public ResponseEntity<Map<String, Object>> getAvailabilityList() {
        try {
            Map<String, Object> response = new HashMap<>();
            List<DateAvailability> availabilityList = DateAvailability.fromDates(orderRepository);
            response.put("availabilityList", availabilityList);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Error while fetching availabilityList"));
        }
    }

    /**
     * Метод для создания заказа, проверяет корректность введённых данных.
        * @param order
        * @param user
        * @param sessionStatus
        * @param bindingResult
        * @return
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createOrder(@RequestBody @Valid Order order,
                              @AuthenticationPrincipal User user,
                              SessionStatus sessionStatus, BindingResult bindingResult) {
        sellerService.check(bindingResult,order);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", bindingResult.getAllErrors().get(0).getDefaultMessage()));
        }
        order.setUser(user);
        orderRepository.save(order);
        userRepository.save(user);
        sessionStatus.setComplete();

        return ResponseEntity.ok(Map.of("message", "Order created"));
    }
}

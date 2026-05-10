package org.example.mrdverkin.config;

import org.example.mrdverkin.dataBase.Entitys.DoorLimits;
import org.example.mrdverkin.dataBase.Entitys.Order;
import org.example.mrdverkin.dataBase.Entitys.Role;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.DoorLimitsRepository;
import org.example.mrdverkin.dataBase.Repository.OrderRepository;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
@Profile("dev")
public class DevUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DoorLimitsRepository doorLimitsRepository;
    @Autowired
    private OrderRepository orderRepository;

    public DevUserInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        String password = passwordEncoder.encode("test");

        userRepository.save(User.builder()
                .username("test")
                .password(password)
                .nickname("shop")
                .roles(Set.of(Role.ROLE_SELLER))
                .build());

        password = passwordEncoder.encode("main");
        userRepository.save(User.builder()
                .username("main")
                .password(password)
                .nickname("main")
                .roles(Set.of(Role.ROLE_MAIN_INSTALLER))
                .build());

        password = passwordEncoder.encode("admin");
        userRepository.save(User.builder()
                .username("admin")
                .password(password)
                .nickname("admin")
                .roles(Set.of(Role.ROLE_ADMIN))
                .build());

        password = passwordEncoder.encode("services");
        userRepository.save(User.builder()
                .username("services")
                .password(password)
                .nickname("services")
                .roles(Set.of(Role.ROLE_SERVICES))
                .build());

        generateDataLimit();
        setUserForOrder();
    }

    private void generateDataLimit() {
        LocalDate today = LocalDate.now();
        LocalDate end = today.plusMonths(2);

        // Генерируем все дни от today до end (включительно)
        for (LocalDate date = today; !date.isAfter(end); date = date.plusDays(1)) {
            DoorLimits doorLimits = DoorLimits.builder()
                    .limitDate(java.sql.Date.valueOf(date))
                    .frontDoorQuantity(2)
                    .inDoorQuantity(50)
                    .build();
            doorLimitsRepository.save(doorLimits);
        }
    }

    private void setUserForOrder(){
        User user = userRepository.findByNickname("shop").orElseThrow();
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
         order.setUser(user);
         orderRepository.save(order);
        }
    }
}



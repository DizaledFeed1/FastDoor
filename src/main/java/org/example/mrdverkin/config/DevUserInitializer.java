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
        Set<Role> roles = new HashSet<>();
        String password = passwordEncoder.encode("test");

        roles.add(Role.ROLE_SELLER);
        userRepository.save(new User("test", password, "shop", roles));

        roles.remove(Role.ROLE_SELLER);
        password = passwordEncoder.encode("main");
        roles.add(Role.ROLE_MAIN_INSTALLER);
        userRepository.save(new User("main", password, "main", roles));

        roles.remove(Role.ROLE_MAIN_INSTALLER);
        password = passwordEncoder.encode("admin");
        roles.add(Role.ROLE_ADMIN);
        userRepository.save(new User("admin", password, "admin", roles));

        roles.remove(Role.ROLE_ADMIN);
        password = passwordEncoder.encode("services");
        roles.add(Role.ROLE_SERVICES);
        userRepository.save(new User("services", password, "services", roles));

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



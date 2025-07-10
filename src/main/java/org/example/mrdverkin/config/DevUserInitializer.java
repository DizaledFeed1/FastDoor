package org.example.mrdverkin.config;

import org.example.mrdverkin.dataBase.Entitys.Role;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
@Profile("dev")
public class DevUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        roles.add(Role.ROLE_MainInstaller);
        userRepository.save(new User("main", password, "main", roles));

        roles.remove(Role.ROLE_MainInstaller);
        password = passwordEncoder.encode("admin");
        roles.add(Role.ROLE_ADMIN);
        userRepository.save(new User("admin", password, "admin", roles));
    }
}



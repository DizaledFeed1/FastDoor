package org.example.mrdverkin.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dto.RegistrationForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registration(RegistrationForm form) {
        User user = userRepository.findByInviteCode(form.getInviteCode())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с кодом приглашения: " +  form.getInviteCode() + " не найден"));

        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));

        userRepository.save(user);
    }
}

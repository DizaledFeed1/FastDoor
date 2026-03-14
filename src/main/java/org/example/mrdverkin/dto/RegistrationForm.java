package org.example.mrdverkin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.example.mrdverkin.dataBase.Entitys.Role;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;
import java.util.Set;

@Data
public class RegistrationForm {
    private String username;
    @NotBlank
    private String password;
    private String confirm;
    private String fullname;
    @NotBlank
    private String role; // todo поменять со Стринги на Role, нужно написать тест регистрации

    private String phoneNumber;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
                username, passwordEncoder.encode(password),
                fullname.toLowerCase(Locale.ROOT), Set.of(Role.valueOf(role)));
    }
    public boolean isPasswordMatching() {
        return password.equals(confirm);
    }
}

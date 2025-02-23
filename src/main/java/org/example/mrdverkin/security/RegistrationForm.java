package org.example.mrdverkin.security;

import lombok.Data;
import org.example.mrdverkin.dataBase.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String confirm;
    private String fullname;
    private String email;
    private String phone;
    private String role;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
                username, passwordEncoder.encode(password),
                fullname,email, phone, Set.of(role));
    }
    public boolean isPasswordMatching() {
        return password.equals(confirm);
    }
}

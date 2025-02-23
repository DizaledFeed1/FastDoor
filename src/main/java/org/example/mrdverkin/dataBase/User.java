package org.example.mrdverkin.dataBase;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "\"User\"")
@NoArgsConstructor(access= AccessLevel.PROTECTED, force=true)
@RequiredArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final String username;
    private final String password;
    private final String fullName;
    private final String email;
    private final String phone;

    @ElementCollection(fetch = FetchType.EAGER) // Связь с таблицей ролей
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private final Set<String> roles; // Может содержать "ROLE_USER", "ROLE_INSTALLER", "ROLE_ADMIN"

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new) // Преобразуем роли в GrantedAuthority
                .collect(Collectors.toList());
    }
}

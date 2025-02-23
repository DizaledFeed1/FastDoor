package org.example.mrdverkin.security;

import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dataBase.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user != null) {
                return user;
            }
            throw new UsernameNotFoundException("User" + username + "found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/main").permitAll()
                        .requestMatchers("/home").hasRole("SELLER")
                        .requestMatchers("/**", "/login", "/register", "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/orders/create").hasAuthority("SCOPE_readOrders")
                        .requestMatchers(HttpMethod.POST, "/orders").hasRole("SCOPE_writeOrders")
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        .disable()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/main")
                        .failureUrl("/login?error=true")
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Разрешить использование фреймов только с того же источника
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .build();
    }
}

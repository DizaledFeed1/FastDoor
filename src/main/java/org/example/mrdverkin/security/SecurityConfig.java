package org.example.mrdverkin.security;

import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.example.mrdverkin.dataBase.Entitys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // Пример с использованием Spring Security для конфигурации AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        // Получаем объект AuthenticationManagerBuilder из http
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        // Устанавливаем провайдер аутентификации
        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder);

        // Возвращаем объект AuthenticationManager
        return authenticationManagerBuilder.build();
    }
    // Теперь возвращаем Optional<User>, а не просто User
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomSuccessHandler customSuccessHandler) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/home/seller/**", "/orders/**").hasRole("SELLER")
                        .requestMatchers("/home/mainInstaller/**").hasRole("MainInstaller")
                        .requestMatchers("edit/**").hasAnyRole("SELLER", "MainInstaller")
                        .requestMatchers("/home/admin").hasRole("ADMIN")
                        .requestMatchers("/style/**", "/images/**", "/**", "/login", "/register", "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/orders/create").hasAuthority("SCOPE_readOrders")
                        .requestMatchers(HttpMethod.POST, "/orders").hasRole("SCOPE_writeOrders")
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .rememberMe(remember -> remember
                        .tokenValiditySeconds(Integer.MAX_VALUE)
                        .key("mrDverkin")
                        .rememberMeParameter("rememberMe")
                )
                .sessionManagement(session -> session
                        .maximumSessions(1) // Один юзер — одна сессия
                        .expiredUrl("/login?expired=true") // Если сессия истекла — кидает на логин
                )
                .sessionManagement(session -> session
                        .sessionFixation().migrateSession() // Новая сессия при каждой авторизации
                        .invalidSessionUrl("/login") // Если сессия стала невалидной
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customSuccessHandler)
                        .failureUrl("/login?error=true")
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Разрешить использование фреймов только с того же источника
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/main")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .build();
    }
}



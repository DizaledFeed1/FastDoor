package org.example.mrdverkin.config;

import org.example.mrdverkin.dataBase.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.remember-me.key}")
    private String rememberMeKey;

    private final String MAININSTALLER = "MAIN_INSTALLER";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .headers(headers -> headers.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login", "/api/register", "/api/csrf","/h2-console/**","/swagger-ui/**", "/v3/api-docs/**", "/api/check-session", "/actuator/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/orders/create", "/api/edit/**", "/api/delete").hasAnyRole(MAININSTALLER,"SELLER")
                        .requestMatchers("/api/orders/**", "/api/list/sellerList").hasAnyRole("SELLER")
                        .requestMatchers("/api/list/adminList", "/api/seller/**", "/api/doorLimits/allDays").hasAnyRole("ADMIN")
                        .requestMatchers( "/api/doorLimits/closeDate", "/api/doorLimits/openDate", "/api/doorLimits/editDate" ,"/api/listInstallers/**", "/api/listInstallers"
                                ,"/api/mainInstaller/**", "/api/installer/**").hasAnyRole(MAININSTALLER)
//                        .requestMatchers("/api/edit/**", "/api/delete").hasAnyRole("SELLER", "MainInstaller")
                        .requestMatchers("/api/list/sort").hasAnyRole("ADMIN", MAININSTALLER)
                        .requestMatchers("/api/hints").authenticated()
                        .anyRequest().authenticated())
                .rememberMe(remember -> remember
                        .rememberMeServices(rememberMeServices)
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/api/logout")
                        .permitAll()
                )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(@Autowired UserRepository userRepository) {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> servletContext.setSessionTimeout(-1);
    }

    @Bean
    public RememberMeServices rememberMeServices(UserRepository userRepository) {
        TokenBasedRememberMeServices services =
                new TokenBasedRememberMeServices(rememberMeKey, userDetailsService(userRepository));
        services.setCookieName("remember-me");
        services.setUseSecureCookie(true);
        return services;
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("https://fast-door.ru", "http://localhost:8081"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(List.of("*"));

        config.setExposedHeaders(List.of("Set-Cookie"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

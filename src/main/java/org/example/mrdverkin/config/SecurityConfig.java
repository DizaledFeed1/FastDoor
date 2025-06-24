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

    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private UserDetailsService userDetailsService;
    @Value("${security.remember-me.key}")
    private String rememberMeKey;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .headers(headers -> headers.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login", "/api/register", "/api/csrf","/h2-console/**","/swagger-ui/**", "/v3/api-docs/**", "/api/check-session").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/orders/create").hasAnyRole("MainInstaller","SELLER")
                        .requestMatchers("/api/orders/**", "/api/list/sellerList", "/api/delete").hasAnyRole("SELLER")
                        .requestMatchers("/api/list/adminList").hasAnyRole("ADMIN")
                        .requestMatchers( "/api/doorLimits/**", "/api/listInstallers/**", "/api/listInstallers"
                                ,"/api/mainInstaller/**", "/api/installer/**").hasAnyRole("MainInstaller")
                        .requestMatchers("/api/edit/**").hasAnyRole("SELLER", "MainInstaller")
                        .requestMatchers("/api/list/sort").hasAnyRole("ADMIN", "MainInstaller")
                        .anyRequest().authenticated())
                .rememberMe(remember -> remember
                        .rememberMeServices(rememberMeServices())
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/api/logout")
                        .permitAll()
                )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
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
    public RememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices services =
                new TokenBasedRememberMeServices(rememberMeKey, userDetailsService());
        services.setCookieName("remember-me");
        services.setUseSecureCookie(false); // <<< отключить secure
        return services;
    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("https://fast-door.ru"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(List.of("*"));

        config.setExposedHeaders(List.of("Set-Cookie"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

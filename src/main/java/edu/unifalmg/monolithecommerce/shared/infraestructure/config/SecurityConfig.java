package edu.unifalmg.monolithecommerce.shared.infraestructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final TokenValidationService tokenValidationService;
    private final String jwtCookieName;

    public SecurityConfig(TokenValidationService tokenValidationService,
                          @Value("${api.security.cookie.name}") String jwtCookieName) {
        this.tokenValidationService = tokenValidationService;
        this.jwtCookieName = jwtCookieName;
    }

    private static final String[] PUBLIC_MATCHERS = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        JwtAuthorizationFilter jwtFilter = new JwtAuthorizationFilter(tokenValidationService, jwtCookieName);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PUBLIC_MATCHERS).permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/categories",
                                "/models/search",
                                "/models"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/users",
                                "/auth/login",
                                "/webhooks/mercadopago"
                        ).permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/models/{id}/zip-key").permitAll()
                        .requestMatchers("/actuator/prometheus").permitAll()
                        .requestMatchers( "/carts/items").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

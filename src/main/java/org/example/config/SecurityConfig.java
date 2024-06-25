package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигурационный класс для Spring Security
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Формирование правил доступа к страницам
     *
     * @param http объект для настройки безопасности http-запросов
     * @return объект правил доступа
     * @throws Exception ошибка при включении защиты csrf или при вызове build()
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth ->
                auth.requestMatchers("/crm/users").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/crm/admins").hasRole("ADMIN")
                        .anyRequest().permitAll());
        return http.build();
    }
}

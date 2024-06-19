package org.example.config;

import org.example.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Конфигурационный класс для Spring Security
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
    /**
     * Настройка хеширования паролей
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * Получение объекта UserService
     * @return сервис загрузки информации о пользователе
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserService();
    }

    /**
     * Формирование логики аутентификации
     * @return объект логики аутентификации
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Получение обработчика успешной аутентификации пользователя
     * @return обработчика успешной аутентификации
     */
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler(){
        return new CustomAuthenticationSuccessHandler();
    }

    /**
     * Формирование правил доступа к страницам
     * @param http объект для настройки безопасности http-запросов
     * @return объект правил доступа
     * @throws Exception ошибка при включении защиты csrf или при вызове build()
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth ->
                        auth.requestMatchers("/crm/users").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/crm/admins").hasRole("ADMIN")
                                .requestMatchers("/login").anonymous()
                                .anyRequest().permitAll())
                .formLogin(login -> login.permitAll().successHandler(customAuthenticationSuccessHandler()));
        return http.build();
    }
}

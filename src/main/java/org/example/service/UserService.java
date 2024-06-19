package org.example.service;

import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис загрузки информации о пользователе
 */
@Service
public class UserService implements UserDetailsService {
    /**
     * Репозиторий для записей о пользователях
     */
    @Autowired
    private UserRepository repository;

    /**
     * Поиск пользователя по логину
     * @param username логин пользователя
     * @return запись пользователя
     * @throws UsernameNotFoundException не удалось найти пользователя
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = repository.findByUsername(username);
        return user.map(CustomUserDetails::new).orElseThrow(()-> new UsernameNotFoundException("No such user"));
    }
}

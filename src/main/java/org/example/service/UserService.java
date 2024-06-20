package org.example.service;

import org.example.dto.UserDto;
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

    /**
     * Регистрация нового пользователя
     * @param userDto DTO-объект пользователя
     * @throws Exception ошибка при попытке добавить пользователя с уже используемым логином или почтой
     */
    public void registerNewUser(UserDto userDto) throws Exception {
        if (!repository.findByEmail(userDto.getEmail()).isEmpty()) {
            throw new Exception("Аккаунт с такой почтой уже существует!");
        }
        if (!repository.findByUsername(userDto.getUsername()).isEmpty()){
            throw new Exception("Аккаунт с таким логином уже существует!");
        }
        UserEntity userEntity = UserEntity.builder().fio(userDto.getFio())
                .username(userDto.getUsername()).password(userDto.getPassword())
                .email(userDto.getEmail()).role(userDto.getRole()).build();
        repository.save(userEntity);
    }
}

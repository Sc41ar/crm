package org.example.service;

import io.jsonwebtoken.Jwts;
import org.example.dto.UserDto;
import org.example.dto.UserLoginDTO;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.example.util.PasswodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Optional;

/**
 * Сервис загрузки информации о пользователе
 */
@Service
public class UserService {


    SecretKey key = Jwts.SIG.HS512.key().build(); // or HS384 or HS256
    /**
     * Репозиторий для записей о пользователях
     */
    @Autowired
    private UserRepository repository;

    /**
     * @param userDto
     * @return
     * @throws Exception
     */
    public String userLogin(UserLoginDTO loginDto) {
        Optional<UserEntity> user = Optional.empty();

        if (loginDto.isEmail()) {
            user = repository.findByEmail(loginDto.getEmailOrUsername());
        }
        user = repository.findByUsername(loginDto.getEmailOrUsername());

        if (user.isEmpty()) {
            return null;
        }

        if (PasswodUtils.isPasswodMatch(loginDto.getPassword(), user.get().getPassword())) {
            return Jwts.builder().subject(user.get().getUsername()).signWith(key).compact();
        }
        return null;
    }

    /**
     * Регистрация нового пользователя
     *
     * @param userDto DTO-объект пользователя
     * @throws Exception ошибка при попытке добавить пользователя с уже используемым логином или почтой
     */
    public void registerNewUser(UserDto userDto) throws Exception {
        if (!repository.findByEmail(userDto.getEmail()).isEmpty()) {
            throw new Exception("Аккаунт с такой почтой уже существует!");
        }
        if (!repository.findByUsername(userDto.getUsername()).isEmpty()) {
            throw new Exception("Аккаунт с таким логином уже существует!");
        }

        UserEntity userEntity = UserEntity.builder().fio(userDto.getFio())
                .username(userDto.getUsername()).password(PasswodUtils.hashPassword(userDto.getPassword()))
                .email(userDto.getEmail()).role(userDto.getRole()).build();
        repository.save(userEntity);
    }
}

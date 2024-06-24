package org.example.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.example.dto.JWTVerifyDTO;
import org.example.dto.UserDto;
import org.example.dto.UserLoginDTO;
import org.example.entity.UserEntity;
import org.example.repository.UserRepository;
import org.example.util.PasswodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Optional;

/**
 * Сервис загрузки информации о пользователе
 */
@Service
@Log4j2
public class UserService {


    SecretKey key = Jwts.SIG.HS512.key().build(); // or HS384 or HS256
    /**
     * Репозиторий для записей о пользователях
     */
    @Autowired
    private UserRepository repository;


    /**
     * Метод для проверки JWT-токена
     *
     * @param jwtTokenDTO DTO-объект с JWT-токеном
     * @return true, если токен действителен, иначе false
     * https://github.com/jwtk/jjwt?tab=readme-ov-file#verification-key
     * for docs
     */
    public boolean jwtVerify(JWTVerifyDTO jwtTokenDTO) {
        // Разделяем токен на три части по разделителю "."
        String[] parts = jwtTokenDTO.getJwtToken().split("\\.");
        // Если токен не состоит из трех частей, то он недействителен
        if (parts.length != 3) {
            return false;
        }

        try {
            // Пытаемся проверить токен с помощью ключа
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtTokenDTO.getJwtToken());
            // Если проверка прошла успешно, то токен действителен
            return true;
        } catch (JwtException e) {
            // Если произошла ошибка при проверке токена, записываем ее в лог и возвращаем false
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * Метод для входа пользователя в систему
     *
     * @param loginDto DTO-объект с данными для входа пользователя
     * @return JWT-токен для аутентификации пользователя, если вход успешен
     * @throws Exception если произошла ошибка при входе пользователя
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

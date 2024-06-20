package org.example.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Log4j2
public class PasswodUtils {

    /** хеширование паролей
     * @param password строка для хеширования
     *
     */
    public static String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            String hashedPassword = passwordEncoder.encode(password);
            return hashedPassword;
        } catch (Exception e) {
            log.warn(e.getLocalizedMessage());
            return password;
        }
    }

    /**
     * Проверка паролей на соответствией
     * @param inputPassword пароль, пришедший с фронта
     * @param dbPassword пароль, который хранится в бд
     * @return значение проверки
     */
    public static boolean isPasswodMatch(String inputPassword, String dbPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(inputPassword, dbPassword);
    }
}

package org.example.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Обработчик при успешной аутентификации
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * Объект для перенаправления пользователей на разные страницы
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * Перенаправление пользователя после успешной аутентификаии
     * @param request запрос, вызвавший успешную аутентификацию
     * @param response ответ
     * @param chain фильтры к http-запросу/ответу
     * @param authentication объект аутентификации
     * @throws IOException ошибка при отправке/записи данных
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        redirect(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    /**
     * Перенаправление пользователя после успешной аутентификаии
     * @param request запрос, вызвавший успешную аутентификацию
     * @param response ответ
     * @param authentication объект аутентификации
     * @throws IOException ошибка при отправке/записи данных
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        redirect(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    /**
     * Перенаправление пользователя на необходимую страницу
     * @param request текущий запрос
     * @param response ответ
     * @param authentication объект аутентификации
     * @throws IOException ошибка при отправке/записи данных
     */
    public void redirect(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineRedirectUrl(authentication);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * Определение страницы для перенаправления по роли пользователя
     * @param authentication объект аутентификации
     * @return url-адрес страницы
     */
    public String determineRedirectUrl(Authentication authentication) {
        Map<String, String> roleUrlMap = new HashMap<>();
        roleUrlMap.put("ROLE_USER", "/crm/users");
        roleUrlMap.put("ROLE_ADMIN", "/crm/admins");
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if(roleUrlMap.containsKey(authorityName)) {
                return roleUrlMap.get(authorityName);
            }
        }
        throw new IllegalStateException();
    }

    /**
     * Очистка атрибутов аутентификации
     * @param request текущий запрос
     */
    public void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}

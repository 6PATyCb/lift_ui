/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.vaadin;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.java_inside.lift_ui.users.User;
import ru.java_inside.lift_ui.users.UserServiceImpl;

/**
 * Фильтр для получения пользователя из текущего потока
 *
 * @author 6PATyCb
 */
@Slf4j
@Component
@Order(1)
public class LiftUserFilter implements Filter {

    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        log.debug(
                "Starting a LiftUserFilter for req : {}",
                req.getRequestURI());
        User user = UserServiceImpl.readUserFromSession(req.getSession());
        try {
            if (user != null) {
                userThreadLocal.set(user);
            }
            chain.doFilter(request, response);
        } finally {
            if (user != null) {
                userThreadLocal.remove();
            }
        }
        log.debug(
                "Ending a LiftUserFilter for req : {}",
                req.getRequestURI());
    }

    public static User getUserStatic() {
        return userThreadLocal.get();
    }

    public User getUser() {
        return userThreadLocal.get();
    }

}

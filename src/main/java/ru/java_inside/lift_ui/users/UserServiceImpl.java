/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.users;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.WrappedSession;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.java_inside.lift_ui.users.names.NamesService;

/**
 *
 * @author 6PATyCb
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String USER_SESSION_KEY = "usr";
    private NamesService namesService;

    @Override
    public User readUserFromSession(WrappedSession session) {
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

    public static User readUserFromSession(HttpSession session) {
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

    @Override
    public User getCurrentUser() {
        WrappedSession session = UI.getCurrent().getSession().getSession();
        User currentUser = readUserFromSession(session);
        if (currentUser == null) {
            String randomName = namesService.getRandomName();
            //чтобы не палить часть sessionId, сделаем от нее md5. Почему берем только первые 6 символов? Чтобы невозможно было восстановить sessionId и чтобы не был сильно длинным toString от пользователя
            String md5Hex = DigestUtils.md5Hex(session.getId().substring(0, 6)).toUpperCase();
            currentUser = new User(md5Hex.substring(0, 6), randomName, Role.PASSENGER);
            session.setAttribute(USER_SESSION_KEY, currentUser);
            log.info("Новый пользователь создан {}", currentUser);
        } else {
            log.debug("Пользователь возвращен из сессии {}", currentUser);
        }
        return currentUser;
    }

    @Override
    public void switchCurrentRole(Role newRole) {
        WrappedSession session = UI.getCurrent().getSession().getSession();
        User currentUser = readUserFromSession(session);
        if (newRole == currentUser.getRole()) {
            return;
        }
        User changedUser = new User(currentUser.getId(), currentUser.getName(), newRole);
        session.setAttribute(USER_SESSION_KEY, changedUser);
        log.info("Изменена роль пользователя {}", changedUser);
    }

    @Autowired
    public void setNamesService(NamesService namesService) {
        this.namesService = namesService;
    }

}

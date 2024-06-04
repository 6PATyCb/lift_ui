/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.vaadin;

import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WrappedSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.java_inside.lift_ui.session.SessionService;

/**
 *
 * @author 6PATyCb
 */
@Component
@Slf4j
public class ApplicationVaadinServiceInitListener implements VaadinServiceInitListener {

    private SessionService sessionService;

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addSessionInitListener(initEvent -> {
            log.info("Новая сессия создана");
            WrappedSession session = initEvent.getSession().getSession();
            sessionService.sessionCreated(session);
            //Добавим обработчик неперехваченных исключений
            VaadinSession.getCurrent().setErrorHandler((ErrorEvent ev) -> {
                Throwable throwable = ev.getThrowable();
                VaadinUtils.showErrorLongNotification(String.format("Произошла непредвиденная ошибка %s", throwable.toString()), throwable);
            });
        });
        event.getSource().addSessionDestroyListener(e -> {
            log.info("Сессия уничтожена");
            WrappedSession session = e.getSession().getSession();
            sessionService.sessionDestroyed(session);

            session.invalidate();
        });
    }

    @Autowired
    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ru.java_inside.lift_ui.session;

import com.vaadin.flow.server.WrappedSession;
import java.util.Map;

/**
 *
 * @author 6PATyCb
 */
public interface SessionService {

    int getSessionsCount();

    Map<String, WrappedSession> getSessions();

    void sessionCreated(WrappedSession session);

    void sessionDestroyed(WrappedSession session);
}

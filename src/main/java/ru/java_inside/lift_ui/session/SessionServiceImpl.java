/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.session;

import com.vaadin.flow.server.WrappedSession;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 *
 * @author 6PATyCb
 */
@Service
public class SessionServiceImpl implements SessionService {

    private final Map<String, WrappedSession> sessions = new ConcurrentHashMap<>();

    @Override
    public int getSessionsCount() {
        return sessions.size();
    }

    @Override
    public Map<String, WrappedSession> getSessions() {
        return Collections.unmodifiableMap(sessions);
    }

    @Override
    public void sessionCreated(WrappedSession session) {
        sessions.put(session.getId(), session);
    }

    @Override
    public void sessionDestroyed(WrappedSession session) {
        sessions.remove(session.getId());
    }

}

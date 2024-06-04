/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.users;

import com.vaadin.flow.server.WrappedSession;

/**
 *
 * @author 6PATyCb
 */
public interface UserService {

    User getCurrentUser();

    User readUserFromSession(WrappedSession session);

    void switchCurrentRole(Role newRole);
}

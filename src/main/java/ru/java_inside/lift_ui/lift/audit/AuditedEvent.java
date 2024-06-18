/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.lift.audit;

import java.time.Clock;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;
import ru.java_inside.lift_ui.users.User;

/**
 *
 * @author 6PATyCb
 */
@Getter
@ToString
public class AuditedEvent extends ApplicationEvent {

    private final boolean save;
    private final User user;
    private final Class className;
    private final String methodName;
    private final Object[] methodArgs;

    public AuditedEvent(boolean save, User user, Class className, String methodName, Object[] methodArgs, Object source) {
        super(source);
        this.save = save;
        this.user = user;
        this.className = className;
        this.methodName = methodName;
        this.methodArgs = methodArgs;
    }

    public AuditedEvent(boolean save, User user, Class className, String methodName, Object[] methodArgs, Object source, Clock clock) {
        super(source, clock);
        this.save = save;
        this.user = user;
        this.className = className;
        this.methodName = methodName;
        this.methodArgs = methodArgs;
    }

}

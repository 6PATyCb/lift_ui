/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.lift.event;

import java.time.Clock;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author 6PATyCb
 */
public class LiftEvent extends ApplicationEvent {

    public LiftEvent(Object source) {
        super(source);
    }

    public LiftEvent(Object source, Clock clock) {
        super(source, clock);
    }

}

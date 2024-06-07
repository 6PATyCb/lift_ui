/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.lift.lift_ride;

import java.time.Clock;
import lombok.Getter;
import lombok.ToString;
import ru.java_inside.lift_ui.lift.event.LiftEvent;
import ru.java_inside.lift_ui.users.User;

/**
 * Событие о поездке пользователя
 *
 * @author 6PATyCb
 */
@Getter
@ToString
public class LiftRideEvent extends LiftEvent {

    private final int ridedFloors;
    private final User user;

    public LiftRideEvent(int ridedFloors, User user, Object source) {
        super(source);
        this.ridedFloors = ridedFloors;
        this.user = user;
    }

    public LiftRideEvent(int ridedFloors, User user, Object source, Clock clock) {
        super(source, clock);
        this.ridedFloors = ridedFloors;
        this.user = user;
    }

    public LiftRide toLiftRide() {
        return new LiftRide(ridedFloors, user, getTimestamp());
    }

}

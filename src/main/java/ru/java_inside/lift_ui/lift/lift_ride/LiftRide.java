/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.lift.lift_ride;

import java.util.Objects;
import lombok.Getter;
import lombok.ToString;
import ru.java_inside.lift_ui.users.User;

/**
 * Поездка в лифте
 *
 * @author 6PATyCb
 */
@Getter
@ToString
public class LiftRide {

    private final int ridedFloors;
    private String userId;
    private long timestamp;

    public LiftRide(int ridedFloors, String userId, long timestamp) {
        this.ridedFloors = ridedFloors;
        this.userId = userId;
        this.timestamp = timestamp;
    }

    public LiftRide(int ridedFloors, User user, long timestamp) {
        this(ridedFloors, user.getId(), timestamp);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.ridedFloors;
        hash = 47 * hash + Objects.hashCode(this.userId);
        hash = 47 * hash + (int) (this.timestamp ^ (this.timestamp >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LiftRide other = (LiftRide) obj;
        if (this.ridedFloors != other.ridedFloors) {
            return false;
        }
        if (this.timestamp != other.timestamp) {
            return false;
        }
        return Objects.equals(this.userId, other.userId);
    }
    
    

}

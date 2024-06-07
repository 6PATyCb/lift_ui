/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.lift;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.java_inside.lift_ui.users.User;

/**
 * Пасажир лифта
 *
 * @author 6PATyCb
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Passenger {

    /**
     * Этаж откуда хочет ехать пассажир
     */
    private final byte fromFloor;
    /**
     * Этаж куда хочет доехать пассажир
     */
    private Byte toFloor;
    private final User user;
    /**
     * Время когда пассажир вошел в лифт
     */
    private final LocalDateTime entered;

    public boolean isTimeToLeave(int maxTimeoutSecs, LocalDateTime now) {
        if (entered == null || now == null) {
            throw new IllegalArgumentException();
        }
        return now.minusSeconds(maxTimeoutSecs).isAfter(entered);
    }

    /**
     * Сколько этажей проехал пассажир
     *
     * @return
     */
    public int getRidedFloors() {
        if (toFloor == null || fromFloor == toFloor) {
            return 0;
        }
        if (fromFloor > toFloor) {
            return fromFloor - toFloor;
        } else {
            return toFloor - fromFloor;
        }
    }
}

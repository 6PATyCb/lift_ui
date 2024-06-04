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
     * Максимальное время нахождения в лифте без указания этажа для поездки
     */
    private static final int MAX_TIME_IN_LIFT_SEC = 15;
    /**
     * Этаж куда хочет доехать пассажир
     */
    private Byte floor;
    private final User user;
    /**
     * Время когда пассажир вошел в лифт
     */
    private final LocalDateTime entered;

    public boolean isTimeToLeave(LocalDateTime now) {
        if (entered == null || now == null) {
            throw new IllegalArgumentException();
        }
        return now.minusSeconds(MAX_TIME_IN_LIFT_SEC).isAfter(entered);
    }
}

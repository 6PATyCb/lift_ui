/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.lift;

import lombok.AllArgsConstructor;
import lombok.ToString;
import ru.java_inside.lift_ui.users.User;

/**
 * Состояние лифта
 *
 * @author 6PATyCb
 */
@AllArgsConstructor
@ToString
public class LiftState {

    /**
     * текущий этаж
     */
    public final byte currentFloor;

    /**
     * этаж последней остановки
     */
    public final byte lastHoldFloor;
    /**
     * Этаж, где ожидают лифт
     */
    public final byte waitFloor;
    /**
     * есть ли пассажир внутри
     */
    public final User user;
    /**
     * Последнее текстовое представление состояния лифта
     */
    public final String lastStateMessage;
    /**
     * Текущее действие лифта
     */
    public final LiftAction action;
    /**
     * Лифт сломан
     */
    public boolean broken;

}

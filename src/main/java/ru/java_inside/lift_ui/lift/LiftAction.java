/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ru.java_inside.lift_ui.lift;

/**
 * Перечень текущих действий лифта
 *
 * @author 6PATyCb
 */
public enum LiftAction {
    /**
     * Пустой лифт ничего не делает
     */
    NO_ACTION_WHEN_EMPTY,
    /**
     * Лифт с пассажиром ничего не делает
     */
    NO_ACTION_WITH_PASSANGER,
    /**
     * Порожнее движение на этаж
     */
    MOVE_TO_FLOOR_WHEN_EMPTY,
    /**
     * движение на этаж c пассажиром
     */
    MOVE_TO_FLOOR_WITH_PASSANGER,
    /**
     * Сломался
     */
    BROKEN,
}

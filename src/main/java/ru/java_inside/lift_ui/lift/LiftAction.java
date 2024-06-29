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
    NO_ACTION_WHEN_EMPTY("Пустой лифт ничего не делает", false),
    /**
     * Лифт с пассажиром ничего не делает
     */
    NO_ACTION_WITH_PASSANGER("Лифт с пассажиром ничего не делает", false),
    /**
     * Порожнее движение на этаж
     */
    MOVE_TO_FLOOR_WHEN_EMPTY("Порожнее движение на этаж", true),
    /**
     * Движение на этаж c пассажиром
     */
    MOVE_TO_FLOOR_WITH_PASSANGER("Движение на этаж c пассажиром", true),
    /**
     * Сломался
     */
    BROKEN("Сломался", false),;

    private final String rusName;
    private final boolean movementAction;

    private LiftAction(String rusName, boolean movementAction) {
        this.rusName = rusName;
        this.movementAction = movementAction;
    }

    public String getRusName() {
        return rusName;
    }

    public boolean isMovementAction() {
        return movementAction;
    }

    @Override
    public String toString() {
        return rusName;
    }

}

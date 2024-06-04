/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ru.java_inside.lift_ui.lift;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Граф переходов между действиями
 *
 * @author 6PATyCb
 */
public enum LiftActionsGraph {
    /**
     * Пустой лифт ничего не делает
     */
    ON_NO_ACTION_WHEN_EMPTY(LiftAction.NO_ACTION_WHEN_EMPTY, PassengerAction.CALL, PassengerAction.STEP_ON),
    /**
     * Лифт с пассажиром ничего не делает
     */
    ON_NO_ACTION_WITH_PASSANGER(LiftAction.NO_ACTION_WITH_PASSANGER, PassengerAction.GO_TO_FLOOR),
    /**
     * Порожнее движение на этаж
     */
    ON_MOVE_TO_FLOOR_WHEN_EMPTY(LiftAction.MOVE_TO_FLOOR_WHEN_EMPTY),
    /**
     * движение на этаж c пассажиром
     */
    ON_MOVE_TO_FLOOR_WITH_PASSANGER(LiftAction.MOVE_TO_FLOOR_WITH_PASSANGER),
    /**
     * когда лифт сломан
     */
    ON_BROKEN(LiftAction.BROKEN, PassengerAction.REPAIR);

    private final LiftAction currenAction;
    private final PassengerAction[] availableActions;

    private LiftActionsGraph(LiftAction currenAction, PassengerAction... availableActions) {
        this.currenAction = currenAction;
        this.availableActions = availableActions;
    }

    public PassengerAction[] getAvailableActions() {
        return availableActions;
    }

    public LiftAction getCurrenAction() {
        return currenAction;
    }

    /**
     * Получение доступный действий относительно текущего действия
     *
     * @param currenAction
     * @return
     */
    public static Set<PassengerAction> getAvailableActions(LiftAction currenAction) {
        Set<PassengerAction> availableActions = new HashSet<>();
        for (LiftActionsGraph graphValue : values()) {
            if (currenAction == graphValue.getCurrenAction()) {
                availableActions.addAll(Arrays.asList(graphValue.getAvailableActions()));
                break;
            }
        }
        return availableActions;
    }

}

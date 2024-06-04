/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ru.java_inside.lift_ui.lift;

import ru.java_inside.lift_ui.users.Role;
import ru.java_inside.lift_ui.users.User;

/**
 * Доступные действия пассажира с лифтом
 *
 * @author 6PATyCb
 */
public enum PassengerAction {
    /**
     * Вызов лифта на этаж
     */
    CALL() {
        @Override
        public boolean isActionAvailable(User user, byte userFloor, LiftState liftState) {
            if (userFloor <= 0) {//запрещены отрицательные этажи
                return false;
            }
            if (liftState.user != null) {//нельзя вызвать лифт, если в нем уже есть пассажир
                return false;
            }
            if (liftState.currentFloor == userFloor) {//нельзя вызвать лифт, если пассажир находится с лифтом на одном этаже
                return false;
            }
            return true;
        }

    },
    /**
     * Войти в лифт
     */
    STEP_ON() {
        @Override
        public boolean isActionAvailable(User user, byte userFloor, LiftState liftState) {
            if (userFloor <= 0) {//запрещены отрицательные этажи
                return false;
            }
            if (liftState.user != null) {//нельзя войти в лифт, если в нем уже есть пассажир
                return false;
            }
            if (liftState.currentFloor != userFloor) {//нельзя войти в лифт, если пассажир не находится с лифтом на одном этаже
                return false;
            }
            return true;
        }

    },
    /**
     * Поехать на этаж
     */
    GO_TO_FLOOR() {
        @Override
        public boolean isActionAvailable(User user, byte userFloor, LiftState liftState) {
            if (userFloor <= 0) {//запрещены отрицательные этажи
                return false;
            }
            if (liftState.user == null) {//некому управлять из кабиты
                return false;
            }
            if (!liftState.user.equals(user)) {//нельзя управлять лифтом из кабины от имени другого пассажира
                // System.out.println("!!1");
                return false;
            }
            if (liftState.currentFloor != userFloor) {//нельзя поехать на этаж, если пассажир уже находится с лифтом на одном этаже
                // System.out.println("!!2");
                return false;
            }
            return true;
        }

    },
    /**
     * Починить лифт
     */
    REPAIR() {
        @Override
        public boolean isActionAvailable(User user, byte userFloor, LiftState liftState) {
            if (userFloor <= 0) {//запрещены отрицательные этажи
                return false;
            }
            if (!liftState.broken) {//нельзя чинить не сломанный лифт
                return false;
            }
            if (user.getRole() != Role.LIFT_ENGENEER) {//только лифтер может чинить лифт
                return false;
            }
            if (liftState.currentFloor != userFloor) {//нельзя чинить лифт не находясь с ним на одном этаже
                return false;
            }
            return true;
        }

    };

    /**
     * Дополнительные проверки текущего состояния лифта для определения
     * доступности этого действия с ним
     *
     * @param user
     * @param userFloor
     * @param liftState
     * @return
     */
    private PassengerAction() {
    }

    public abstract boolean isActionAvailable(User user, byte userFloor, LiftState liftState);
}

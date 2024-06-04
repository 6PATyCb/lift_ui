/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ru.java_inside.lift_ui.lift;

import ru.java_inside.lift_ui.users.User;

/**
 *
 * @author 6PATyCb
 */
public interface LiftService {

    /**
     * Вызов лифта
     *
     * @param floor этаж на который вызывают
     */
    void call(byte floor);

    /**
     * Получение состояния лифта
     *
     * @return
     */
    LiftState getLiftState();

    /**
     * Войти в лифт
     *
     * @param user
     */
    void stepOn(User user);

    /**
     * Поездка на этаж
     *
     * @param user
     * @param floor этаж на который нужно ехать на лифте
     */
    void goToFloor(User user, byte floor);

    /**
     * Починить лифт
     *
     * @param user
     */
    void repair(User user);
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.vaadin;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

/**
 * Иконки для типовых операций
 *
 * @author 6PATyCb
 */
public final class LiftUiIcons {

    private LiftUiIcons() {
    }

    /**
     * Для операции добавления
     *
     * @return
     */
    public static Icon add() {
        return VaadinIcon.PLUS.create();
    }

    /**
     * Для операции изменения
     *
     * @return
     */
    public static Icon edit() {
        return VaadinIcon.PENCIL.create();
    }

    /**
     * Для операции удаления
     *
     * @return
     */
    public static Icon delete() {
        return VaadinIcon.TRASH.create();
    }

    /**
     * Для операции обновления данных
     *
     * @return
     */
    public static Icon refresh() {
        return VaadinIcon.REFRESH.create();
    }

    /**
     * Для операции cинхронизации
     *
     * @return
     */
    public static Icon sync() {
        return VaadinIcon.EXCHANGE.create();
    }

    /**
     * Для операции чтения данных
     *
     * @return
     */
    public static Icon read() {
        return VaadinIcon.AREA_SELECT.create();
    }

    /**
     * Для операции просмотра деталей
     *
     * @return
     */
    public static Icon viewDetails() {
        return VaadinIcon.EYE.create();
    }

    /**
     * Для операции сохранения/подтверждения
     *
     * @return
     */
    public static Icon save() {
        return VaadinIcon.CHECK.create();
    }

    /**
     * Для операции отмены
     *
     * @return
     */
    public static Icon cancel() {
        return VaadinIcon.CLOSE.create();
    }

    /**
     * Для операции активации
     *
     * @return
     */
    public static Icon enable() {
        return VaadinIcon.PLAY.create();
    }

    /**
     * Для операции приостановки
     *
     * @return
     */
    public static Icon disable() {
        return VaadinIcon.PAUSE.create();
    }

    /**
     * Для помощи
     *
     * @return
     */
    public static Icon help() {
        return VaadinIcon.LIGHTBULB.create();
    }

}

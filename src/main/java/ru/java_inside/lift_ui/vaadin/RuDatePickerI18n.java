/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.vaadin;

import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import java.util.Arrays;
import ru.java_inside.lift_ui.utils.DateUtils;

/**
 *
 * @author 6PATyCb
 */
public class RuDatePickerI18n extends DatePickerI18n {

    public static RuDatePickerI18n getInstance() {
        return new RuDatePickerI18n();
    }

    private RuDatePickerI18n() {
        super();
        setCancel("Отмена")
                .setToday("Сегодня")
                .setFirstDayOfWeek(1)
                .setMonthNames(DateUtils.MONTHS)
                .setWeekdays(Arrays.asList("Воскресенье", "Понедельник", "Вторник",
                        "Среда", "Четверг", "Пятница",
                        "Суббота"))
                .setWeekdaysShort(Arrays.asList("Вс", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб")
                );
    }

}

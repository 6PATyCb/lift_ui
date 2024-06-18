/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui;

/**
 * Утилитарный класс для определения порядка проксирования спринговых бинов. Чем
 * меньше значение, тем первее будет использован данный прокси среди прочих.
 * Неправильный порядок использования прокси может приводить к ошибкам или к
 * просадке в производительности.
 *
 * @author 6PATyCb
 */
public final class SpringProxyOrder {

    /**
     * Транзакции JDBC
     */
    public static final int TX_ORDER = 3;
    /**
     * Аудит вызовов бинов, которые сохраняют или удаляют важные данные. См.
     * класс AuditProxyComponent
     */
    public static final int AUDIT_ORDER = 5;
    /**
     * Кэширование методов через Spring cache abstraction
     */
    public static final int CACHE_ORDER = 10;

    private SpringProxyOrder() {
    }

}

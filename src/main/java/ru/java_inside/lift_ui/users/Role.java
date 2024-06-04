/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ru.java_inside.lift_ui.users;

/**
 *
 * @author 6PATyCb
 */
public enum Role {
    PASSENGER("Пассажир", "Обычный пользователь лифта"),
    LIFT_ENGENEER("Лифтер", "Тот кто может починить лифт");

    private final String name;
    private final String description;

    private Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}

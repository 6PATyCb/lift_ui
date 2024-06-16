/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.users.names;

import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author 6PATyCb
 */
@Getter
@ToString
public class Name {

    private final int id;
    private final String name;
    private final boolean female;

    public Name(int id, String name, boolean female) {
        this.id = id;
        this.name = name;
        this.female = female;
    }

    /**
     * Конструктор для сохранения новых записей
     *
     * @param name
     * @param female
     */
    public Name(String name, boolean female) {
        this(0, name, female);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Name other = (Name) obj;
        return this.id == other.id;
    }

}

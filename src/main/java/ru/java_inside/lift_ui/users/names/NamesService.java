/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ru.java_inside.lift_ui.users.names;

import java.util.List;

/**
 *
 * @author 6PATyCb
 */
public interface NamesService {

    /**
     * Получение случайного имени
     *
     * @return
     */
    String getRandomName();

    int saveName(Name name);

    void deleteName(Name name);

    Name getNameById(int id);

    List<Name> getAllNames();
}

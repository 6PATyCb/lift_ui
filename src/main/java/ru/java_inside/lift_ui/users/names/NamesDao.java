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
public interface NamesDao {

    String getRandomName();

    int createName(Name newName);

    void updateName(Name changedName);

    void deleteName(Name name);

    List<Name> getAllNames();

    Name getNameById(int id);
}

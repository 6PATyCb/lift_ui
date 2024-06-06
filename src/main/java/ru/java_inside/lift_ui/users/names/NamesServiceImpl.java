/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.users.names;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 6PATyCb
 */
@Service
public class NamesServiceImpl implements NamesService {

    private NamesDao namesDao;

    @Override
    public String getRandomName() {
        return namesDao.getRandomName();
    }

    @Autowired
    public void setNamesDao(NamesDao namesDao) {
        this.namesDao = namesDao;
    }

}

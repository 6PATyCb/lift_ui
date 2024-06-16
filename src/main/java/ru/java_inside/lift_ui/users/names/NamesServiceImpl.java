/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.users.names;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public int saveName(Name name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        if (name.getId() <= 0) {
            return namesDao.createName(name);
        } else {
            namesDao.updateName(name);
            return name.getId();
        }
    }

    @Override
    public void deleteName(Name name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        namesDao.deleteName(name);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteNames(Collection<Name> names) {
        if (names == null || names.isEmpty()) {
            return;
        }
        for (Name name : names) {
            namesDao.deleteName(name);
        }
    }

    @Override
    public List<Name> getAllNames() {
        return namesDao.getAllNames();
    }

    @Override
    public Name getNameById(int id) {
        return namesDao.getNameById(id);
    }

    @Autowired
    public void setNamesDao(NamesDao namesDao) {
        this.namesDao = namesDao;
    }

}

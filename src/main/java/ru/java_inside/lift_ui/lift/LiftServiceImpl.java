/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.lift;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.java_inside.lift_ui.users.User;

/**
 *
 * @author 6PATyCb
 */
@Slf4j
@Service
public class LiftServiceImpl implements LiftService {

    private LiftEngine liftEngine;

    @Override
    public void call(byte floor) {
        liftEngine.call(floor);
    }

    @Override
    public LiftState getLiftState() {
        return liftEngine.getCurrentLiftState();
    }

    @Override
    public void stepOn(User user) {
        liftEngine.stepOn(user);
    }

    @Override
    public void goToFloor(User user, byte floor) {
        liftEngine.goToFloor(user, floor);
    }

    @Override
    public void repair(User user) {
        liftEngine.repair(user);
    }

    @Autowired
    public void setLiftEngine(LiftEngine liftEngine) {
        this.liftEngine = liftEngine;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.lift.lift_ride;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 *
 * @author 6PATyCb
 */
@Service
public class LiftRideServiceImpl implements LiftRideService, ApplicationListener<LiftRideEvent> {

    private LiftRideDao liftRideDao;

    @Override
    public void onApplicationEvent(LiftRideEvent event) {
        liftRideDao.saveLiftRide(event.toLiftRide());
    }

    @Override
    public void saveLiftRide(LiftRide liftRide) {
        liftRideDao.saveLiftRide(liftRide);
    }

    @Override
    public List<LiftRide> getLast100LiftRides() {
        return liftRideDao.getLast100LiftRides();
    }

    @Autowired
    public void setLiftRideDao(LiftRideDao liftRideDao) {
        this.liftRideDao = liftRideDao;
    }

}

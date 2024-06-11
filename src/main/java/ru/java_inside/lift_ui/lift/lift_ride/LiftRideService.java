/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ru.java_inside.lift_ui.lift.lift_ride;

import java.util.List;

/**
 *
 * @author 6PATyCb
 */
public interface LiftRideService {

    void saveLiftRide(LiftRide liftRide);

    List<LiftRide> getLast100LiftRides();
}

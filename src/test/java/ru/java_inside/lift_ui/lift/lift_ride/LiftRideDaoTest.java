/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ru.java_inside.lift_ui.lift.lift_ride;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.java_inside.lift_ui.MyWebAppConfigTst;

/**
 *
 * @author 6PATyCb
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MyWebAppConfigTst.class)
@SpringBootTest
public class LiftRideDaoTest {

    @Autowired
    private LiftRideDao liftRideDao;
    @Autowired
    private Clock clock;

    @Test
    public void testLiftRideDao() {
        System.out.println("testLiftRideDao");
        LocalDateTime now = LocalDateTime.now(clock);
        Timestamp timestampNow = Timestamp.valueOf(now);
        LiftRide liftRide = new LiftRide(3, "123456", timestampNow.getTime());
        liftRideDao.saveLiftRide(liftRide);
        List<LiftRide> last100LiftRides = liftRideDao.getLast100LiftRides();
        Assert.assertTrue(last100LiftRides.size() == 1);
        Assert.assertEquals(liftRide, last100LiftRides.iterator().next());
    }

}

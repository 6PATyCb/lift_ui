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
import org.springframework.test.context.junit4.SpringRunner;
import ru.java_inside.lift_ui.MyWebAppConfigTst;

/**
 *
 * @author 6PATyCb
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyWebAppConfigTst.class)
public class LiftRideServiceTest {

    @Autowired
    private LiftRideService liftRideService;
    @Autowired
    private Clock clock;

    @Test
    public void testLiftRideService() {
        System.out.println("testLiftRideService");
        {
            LocalDateTime now = LocalDateTime.now(clock);
            Timestamp timestampNow = Timestamp.valueOf(now);
            LiftRide liftRide = new LiftRide(3, "123456", timestampNow.getTime());
            liftRideService.saveLiftRide(liftRide);
            List<LiftRide> last100LiftRides = liftRideService.getLast100LiftRides();
            Assert.assertTrue(last100LiftRides.size() == 1);
            Assert.assertEquals(liftRide, last100LiftRides.iterator().next());
        }
        {//проверим корректную работу кэша
            List<LiftRide> last100LiftRides = liftRideService.getLast100LiftRides();
            List<LiftRide> cachedLast100LiftRides = liftRideService.getLast100LiftRides();
            Assert.assertTrue(last100LiftRides == cachedLast100LiftRides);
        }
        {//проверим корректную работу очистки кэша
            List<LiftRide> last100LiftRides = liftRideService.getLast100LiftRides();

            LocalDateTime now = LocalDateTime.now(clock);
            Timestamp timestampNow = Timestamp.valueOf(now);
            LiftRide liftRide = new LiftRide(4, "345678", timestampNow.getTime());
            //сохранение должно очистить кэш
            liftRideService.saveLiftRide(liftRide);

            List<LiftRide> cachedLast100LiftRides = liftRideService.getLast100LiftRides();
            Assert.assertTrue(last100LiftRides != cachedLast100LiftRides);

        }
    }

}

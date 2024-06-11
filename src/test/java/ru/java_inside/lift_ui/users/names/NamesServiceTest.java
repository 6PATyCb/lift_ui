/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ru.java_inside.lift_ui.users.names;

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
public class NamesServiceTest {

    @Autowired
    private NamesService namesService;

    /**
     * Test of getRandomName method, of class NamesService.
     */
    @Test
    public void testGetRandomName() {
        System.out.println("getRandomName");
        namesService.getRandomName();
        Assert.assertTrue(true);
    }

}

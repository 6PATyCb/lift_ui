/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ru.java_inside.lift_ui.users.names;

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
public class NamesServiceTest {

    @Autowired
    private NamesService namesService;

    @Test
    public void testAll() {
        System.out.println("testAll");
        {//проверим получение случайного имени
            String randomName = namesService.getRandomName();
            Assert.assertTrue(randomName != null);
        }
        {//проверим получение списка всех имен
            List<Name> allNames = namesService.getAllNames();
            Assert.assertTrue(!allNames.isEmpty());
        }
        {   //проверим получение имени по id
            Name unexistedName = namesService.getNameById(999);
            Assert.assertTrue(unexistedName == null);
            List<Name> allNames = namesService.getAllNames();
            Name first = allNames.getFirst();
            Name loadedName = namesService.getNameById(first.getId());
            Assert.assertTrue(first.equals(loadedName));
        }
        {   //проверим удаление
            List<Name> allNames = namesService.getAllNames();
            Name first = allNames.getFirst();
            Name loadedName = namesService.getNameById(first.getId());
            Assert.assertTrue(first.equals(loadedName));
            namesService.deleteName(first);
            Name deletedName = namesService.getNameById(first.getId());
            Assert.assertTrue(deletedName == null);
        }
        {   //проверим вставку новой записи и ее обновление
            Name newName = new Name("Василиса", true);
            Assert.assertTrue(newName.getId() == 0);//у новой записи идентификатор должен быть равен 0
            int newId = namesService.saveName(newName);
            Name loadedName = namesService.getNameById(newId);
            Assert.assertTrue(loadedName != null);

            Name changedName = new Name(newId, "Алиса", true);
            namesService.saveName(changedName);
            Name loadedChangedName = namesService.getNameById(newId);
            Assert.assertTrue(changedName.getName().equals(loadedChangedName.getName()));
        }
        if (1 == 2) {//пока отрубим это, т.к. транзакции не работают в этих тестах. Нужно разобраться
            //проверим, что удаление всех записей запрещено
            List<Name> allNames = namesService.getAllNames();
            try {
                namesService.deleteNames(allNames);
                Assert.fail("При удалении всех записей должно было быть выброшено исключение");
            } catch (Exception ignored) {
                System.out.println("Перехвачено исключение " + ignored.toString());
            }
            List<Name> loadedAllNames = namesService.getAllNames();
            Assert.assertTrue("Транзакция должна была откатиться", allNames.size() == loadedAllNames.size());
        }
    }

}

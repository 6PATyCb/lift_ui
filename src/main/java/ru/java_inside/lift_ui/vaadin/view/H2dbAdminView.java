/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.vaadin.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author 6PATyCb
 */
@Route(value = "h2db_admin", layout = MainAppLayout.class)
@PageTitle("Админка H2DB")
public class H2dbAdminView extends VerticalLayout {

    public H2dbAdminView(
            @Value("${spring.h2.console.path}") String h2dbPath,
            @Value("${spring.datasource.url}") String h2dbJdbcUrl,
            @Value("${spring.datasource.username}") String userName,
            @Value("${spring.datasource.password}") String password
    ) {
        TextArea infoTextArea = new TextArea();
        infoTextArea.setWidth("100%");
        infoTextArea.setHeight("150px");
        infoTextArea.setValue(
                "H2DB - это маленькая СУБД, которая запускается вместе с вашим приложением и работает в памяти.\n"
                + "Редактируйте файлы schema.sql и data.sql для управления ее первичным содержимым. \n"
                + "Для входа в админку H2DB введите учетные данные:\n"
                + "JDBC URL: " + h2dbJdbcUrl + "\n"
                + "User name: " + userName + "\n"
                + "Password: " + password + "\n"
        );
        infoTextArea.setReadOnly(true);

        Button linkButton = new Button("Открыть в отдельном окне", e -> {
            //  UI.getCurrent().getPage().setLocation(h2dbPath);
            UI.getCurrent().getPage().open(h2dbPath, "_blank");
        });

        IFrame iFrame = new IFrame(h2dbPath);
        iFrame.setSizeFull();
        add(infoTextArea);
        add(linkButton);
        add(iFrame);
        setSizeFull();
    }

}

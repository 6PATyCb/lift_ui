/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.vaadin.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import ru.java_inside.lift_ui.session.SessionService;
import ru.java_inside.lift_ui.users.User;
import ru.java_inside.lift_ui.users.UserService;
import ru.java_inside.lift_ui.vaadin.LiftUiIcons;
import ru.java_inside.lift_ui.vaadin.VaadinUtils;

/**
 *
 * @author 6PATyCb
 */
@Route(value = "online", layout = MainAppLayout.class)
@PageTitle("Пользователи онлайн")
public class OnlineUsersListView extends VerticalLayout {

    private final Grid<User> grid = new Grid<>(User.class, true);
    private final Span countSpan = new Span();
    private final UserService userService;
    private final SessionService sessionService;

    public OnlineUsersListView(
            @Autowired UserService userService,
            @Autowired SessionService sessionService
    ) {
        this.userService = userService;
        this.sessionService = sessionService;
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(new Button("Обновить", LiftUiIcons.refresh(), e -> {
            update();
        }));
        buttonsLayout.add(VaadinUtils.createHelpButton("Отображает работающих в системе пользователей. Информация о пользователях берется из HTTP сессий приложения. Новый пользователь создается при первом обращении к приложению"));
        add(buttonsLayout);
        add(grid);
        add(countSpan);
        setSizeFull();

        update();
    }

    private void update() {
        List<User> users = sessionService.getSessions().entrySet().stream()
                .map(Entry::getValue)
                .map(session -> userService.readUserFromSession(session))
                .filter(user -> user != null)
                .collect(Collectors.toList());
        grid.setItems(users);
        countSpan.setText(String.format("Записей: %d", users.size()));
    }

}

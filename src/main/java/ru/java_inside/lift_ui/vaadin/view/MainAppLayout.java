/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.vaadin.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import ru.java_inside.lift_ui.LiftUiApplication;
import ru.java_inside.lift_ui.users.User;
import ru.java_inside.lift_ui.users.UserService;

public class MainAppLayout extends AppLayout {

    private final static String APP_NAME = "Имитатор работы лифта";
    private final UserService userService;
    private final H1 logo = new H1(APP_NAME);
    private final MenuBar menuBar = new MenuBar();
    private final MenuItem logoutMenuItem = menuBar.addItem("Выход");

    public MainAppLayout(
            @Autowired UserService userService
    ) {
        this.userService = userService;

        final UI ui = UI.getCurrent();
        ui.setLocale(LiftUiApplication.LOCALE);
        ui.addAfterNavigationListener((event) -> {
            Component currentView = UI.getCurrent().getCurrentView();
            PageTitle pageTitle = currentView.getClass().getAnnotation(PageTitle.class);
            if (pageTitle != null) {
                logo.setText(String.format("%s / %s", APP_NAME, pageTitle.value()));
            } else {
                logo.setText(APP_NAME);
            }
            updateExitButton();
        });
        createHeader();
        createDrawer();
        getElement().getStyle().set("height", "100%");
    }

    private void createHeader() {

        logo.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);

        logoutMenuItem.getSubMenu().addItem("Подтвердить выход",
                e -> {
                    UI.getCurrent().navigate("/");
                    VaadinSession session = UI.getCurrent().getSession();
                    session.close();
                }
        );

        var mainLayoutH = new HorizontalLayout(new DrawerToggle(), logo, menuBar);
        mainLayoutH.setFlexGrow(1, logo);
        mainLayoutH.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        mainLayoutH.setWidthFull();
        mainLayoutH.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(mainLayoutH);

    }

    private void createDrawer() {
        SideNavItem main = new SideNavItem("Главная", MainPageView.class, VaadinIcon.FLAG.create());

        SideNavItem liftState = new SideNavItem("Состояние лифта", LiftStateView.class, VaadinIcon.BUILDING.create());
        SideNavItem usersOnline = new SideNavItem("Пользователи онлайн", OnlineUsersListView.class, VaadinIcon.GROUP.create());
        SideNavItem liftRides = new SideNavItem("История поездок", LiftRideListView.class, VaadinIcon.USER_CLOCK.create());
        SideNavItem h2Admin = new SideNavItem("Админка H2DB", H2dbAdminView.class, VaadinIcon.DATABASE.create());

        SideNav nav = new SideNav();
        nav.addItem(main, liftState, usersOnline, liftRides, h2Admin);
        addToDrawer(nav);
    }

    private void updateExitButton() {
        User currentUser = userService.getCurrentUser();
        logoutMenuItem.setText(String.format("%s (Выход)", currentUser.toString()));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        updateExitButton();
    }

}

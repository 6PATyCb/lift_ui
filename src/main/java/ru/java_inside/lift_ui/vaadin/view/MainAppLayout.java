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
import java.util.Arrays;
import java.util.LinkedList;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ru.java_inside.lift_ui.LiftUiApplication;
import ru.java_inside.lift_ui.users.User;
import ru.java_inside.lift_ui.users.UserService;
import ru.java_inside.lift_ui.vaadin.VaadinUtils;

public class MainAppLayout extends AppLayout {

    private static final Byte[] CODE = {-103, -59, -117, 111, -58, 111, -117, -59, -103};
    private final static String APP_NAME = "Имитатор работы лифта";
    private final UserService userService;
    private final H1 logo = new H1(APP_NAME);
    private final MenuBar menuBar = new MenuBar();
    private final MenuItem logoutMenuItem = menuBar.addItem("Выход");
    private final LinkedList<Byte> codeHolder = new LinkedList<>();

    public MainAppLayout(
            @Autowired UserService userService
    ) {
        this.userService = userService;

        final UI ui = UI.getCurrent();
        ui.setLocale(LiftUiApplication.LOCALE);
        ui.addAfterNavigationListener((event) -> {
            Component currentView = UI.getCurrent().getCurrentView();
            codeHolder.add(DigestUtils.md5(currentView.getClass().toString())[0]);
            if (codeHolder.size() > CODE.length) {
                codeHolder.removeFirst();
            }
            if (String.format(" %s ", Arrays.toString(codeHolder.toArray(Byte[]::new))).equals(new String(d1("IFstMTAyLCAtNTgsIC0xMTYsIDExMCwgLTU3LCAxMTAsIC0xMTYsIC01OCwgLTEwMl0g")))) {
                String msg = new String(d2("0KPRgNCwISDQotGLINC80L7Qu9C+0LTQtdGGLCDRgtGLINC90LDRiNC10Lsg0L/QsNGB0YXQsNC70LrRgy4g0JrQsNC6INGC0Ysg0L/QvtC90Y/Quywg0LDQu9Cz0L7RgNC40YLQvCDRiNC40YTRgNC+0LLQsNC90LjRjyDQv9C+0LvQvdCw0Y8g0YXRg9C50L3Rjy4g0K3RgtC+INC/0YDQvtGB0YLQviBiYXNlNjQuINCd0LjQutC+0LzRgyDQvdC1INCz0L7QstC+0YDQuCwg0LAg0YLQviDQsdGD0LTQtdGCINC90LUg0LjQvdGC0LXRgNC10YHQvdC+", codeHolder.toArray(Byte[]::new)));
                if ("aa141bd1a87a48962b20d16c4c7ca3a8".equals(DigestUtils.md5Hex(msg))) {
                    VaadinUtils.showErrorLongNotification(msg);
                }
            }
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

    private static byte[] d1(String s) {
        char[] A = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        int a = A.length >> 5;
        int[] t = new int[a << a << a << a];
        for (int i = 0; i < A.length; i++) {
            t[A[i]] = i;
        }
        int m = 0xFF;
        int j = 0;
        byte[] b = new byte[s.length() * 3 / (a << 1)];
        for (int i = 0; i < s.length(); i += (a << 1)) {
            int c0 = t[s.charAt(i)];
            int c1 = t[s.charAt(i + 1)];
            b[j++] = (byte) (((c0 << a) | (c1 >> (a << 1))) & m);
            if (j >= b.length) {
                return b;
            }
            int c2 = t[s.charAt(i + a)];
            b[j++] = (byte) (((c1 << (a << 1)) | (c2 >> a)) & m);
            if (j >= b.length) {
                return b;
            }
            int c3 = t[s.charAt(i + 3)];
            b[j++] = (byte) (((c2 << 6) | c3) & m);
        }
        return b;
    }

    private static byte[] d2(String s, Byte[] code) {
        char[] A = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
        code[3] = (byte) (code[3] >> 3);
        int a = A.length >> 5;
        int[] t = new int[a << a << a << a];
        for (int i = 0; i < A.length; i++) {
            t[A[i]] = i;
        }
        int m = 0xFF;
        int j = 0;
        byte[] b = new byte[s.length() * (code.length - 6) / (a << 1)];
        for (int i = 0; i < s.length(); i += (a << 1)) {
            int c0 = t[s.charAt(i)];
            int c1 = t[s.charAt(i + 1)];
            b[j++] = (byte) (((c0 << a) | (c1 >> (a << 1))) & m);
            if (j >= b.length) {
                return b;
            }
            int c2 = t[s.charAt(i + a)];
            b[j++] = (byte) (((c1 << (a << 1)) | (c2 >> a)) & m);
            if (j >= b.length) {
                return b;
            }
            int c3 = t[s.charAt(i + (code.length - 6))];
            b[j++] = (byte) (((c2 << (code.length - 3)) | c3) & m);
        }
        return b;
    }

}

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
import com.vaadin.flow.server.WrappedSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.time.Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ru.java_inside.lift_ui.LiftUiApplication;
import ru.java_inside.lift_ui.MyWebAppConfig;
import ru.java_inside.lift_ui.users.User;
import ru.java_inside.lift_ui.users.UserService;

public class MainAppLayout extends AppLayout {

    private final static String APP_NAME = "Имитатор работы лифта";
    private final Clock clock;
    //   private final SecurityService securityService;
    private final UserService userService;
    private final H1 logo = new H1(APP_NAME);
    private final MenuBar menuBar = new MenuBar();
    private final MenuItem logoutMenuItem = menuBar.addItem("Выход");
    private final String springProfile;

    public MainAppLayout(
            //    @Autowired SecurityService securityService,
            @Autowired Clock clock,
            @Autowired UserService userService,
            @Value(MyWebAppConfig.PROFILE) String springProfile
    ) {
        //  this.securityService = securityService;
        this.userService = userService;
        this.clock = clock;
        this.springProfile = springProfile;

        final UI ui = UI.getCurrent();
        ui.setLocale(LiftUiApplication.LOCALE);
//        ui.addBeforeLeaveListener((event) -> {
//            System.out.println("!addBeforeLeaveListener! " + event);
//        });
        WrappedSession session = UI.getCurrent().getSession().getSession();
        ui.addAfterNavigationListener((event) -> {
            //   System.out.println("!addAfterNavigationListener! " + event.getLocation().getPath());
            //   System.out.println("!getCurrentView! " + UI.getCurrent().getCurrentView());
            Component currentView = UI.getCurrent().getCurrentView();
            PageTitle pageTitle = currentView.getClass().getAnnotation(PageTitle.class);
            if (pageTitle != null) {
                logo.setText(String.format("%s / %s", APP_NAME, pageTitle.value()));
            } else {
                logo.setText(APP_NAME);
            }
            updateExitButton();
        });
        //  ui.setDirection(Direction.RIGHT_TO_LEFT);
        //  System.out.println("!!getLocale " + ui.getLocale());
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
                    //    WrappedSession session1 = session.getSession();
                    session.close();
                    //    session1.invalidate();

                    //  UI.getCurrent().close();
                    // session.invalidate();
                    //        securityService.logout();\
                    //   VaadinUtils.showErrorNotification("Еще не реализовано");
                }
        );

        var mainLayoutH = new HorizontalLayout(new DrawerToggle(), logo, menuBar);
        //    mainLayoutH.add(VaadinUtils.createVersionComponent(clock, buildProperties, springProfile));
        mainLayoutH.setFlexGrow(1, logo);
        mainLayoutH.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        mainLayoutH.setWidthFull();
        mainLayoutH.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(mainLayoutH);

    }

    private void createDrawer() {
//        MenuBar menuBar = new MenuBar();
//        menuBar.setOpenOnHover(true);
//
//        MenuItem options = menuBar.addItem("Options");
//        SubMenu shareSubMenu = options.getSubMenu();
//        shareSubMenu.addItem("By email").setEnabled(false);
//        shareSubMenu.addItem("Get Link");
//
//        addToDrawer(menuBar);

        SideNavItem main = new SideNavItem("Главная", MainPageView.class, VaadinIcon.FLAG.create());

        SideNavItem liftState = new SideNavItem("Состояние лифта", LiftStateView.class, VaadinIcon.BUILDING.create());
        SideNavItem usersOnline = new SideNavItem("Пользователи онлайн", OnlineUsersListView.class, VaadinIcon.GROUP.create());
//        services.setPrefixComponent(VaadinIcon.EXCHANGE.create());
//        addSideNavItem(TestEmdppKafkaServiceView.class, services);
//        addSideNavItem(TestEmdppRestServiceView.class, services);
//        addSideNavItem(TestCbdgrNpRestServiceView.class, services);
        //      addSideNavItem(NormsInputView.class, services);
//        SideNavItem sidkd = new SideNavItem("Сервисы коммерческой диспетчеризации");
//        {//SIDKD сервисы коммерческой диспетчеризации
//            addSideNavItem(TestSkppServiceView.class, sidkd);
//            addSideNavItem(TestSkpoServiceView.class, sidkd);
//            addSideNavItem(TestSkpOprServiceView.class, sidkd);
//            addSideNavItem(TestSkpopServiceView.class, sidkd);
//            addSideNavItem(TestSkprServiceView.class, sidkd);
//            addSideNavItem(TestSkpGeServiceView.class, sidkd);
//        }
//        services.addItem(sidkd);
//
//        addSideNavItem(TsToIuscupServiceView.class, services);
//        addSideNavItem(TestIommFtpServiceView.class, services);
//        addSideNavItem(TestDataForCupServiceView.class, services);
//        addSideNavItem(KasantServiceView.class, services);
//        addSideNavItem(EasaprServiceView.class, services);
//        addSideNavItem(IsugtWeatherServiceView.class, services);
//        addSideNavItem(IhAvgdGruz25Du25ServiceTestView.class, services);
//        addSideNavItem(TestAsutLocRemontServiceView.class, services);
//        addSideNavItem(SelfTestServicesView.class, services);
//
//        SideNavItem settings = new SideNavItem("Настройки");
//        settings.setPrefixComponent(VaadinIcon.COGS.create());
//        addSideNavItem(DynamicTaskEditorView.class, settings);
//        addSideNavItem(DataForCupJsonEditorView.class, settings);
//        addSideNavItem(WebServiceUrlsEditorView.class, settings);
//        addSideNavItem(IpSecurityEditorView.class, settings);
//        addSideNavItem(CacheView.class, settings);

        SideNav nav = new SideNav();
        nav.addItem(main, liftState, usersOnline);
        //nav.addItem(main, services, settings);
        {
//            Class adminOnlyPageViewClass = AdminOnlyPageView.class;
//            if (VaadinUtils.checkSecurityView(securityService, adminOnlyPageViewClass)) {
//                nav.addItem(createSideNavItem(adminOnlyPageViewClass));
//            }
        }
        addToDrawer(nav);
    }

    private void addSideNavItem(Class viewClass, SideNavItem addTo) {
//        if (!VaadinUtils.checkSecurityView(securityService, viewClass)) {
//            return;
//        }
        addTo.addItem(createSideNavItem(viewClass));
    }

    /**
     * Раскрытие/схлопывание пункта меню
     *
     * @param sideNavItem
     */
    private void expandCollapse(SideNavItem sideNavItem) {
        sideNavItem.setExpanded(!sideNavItem.isExpanded());
    }

    private SideNavItem createSideNavItem(Class viewClass) {
        String label = viewClass.getName();
        {//Название из аннотации
            PageTitle pageTitle = (PageTitle) viewClass.getAnnotation(PageTitle.class);
            if (pageTitle != null) {
                label = pageTitle.value();
            }
        }
        return new SideNavItem(label, viewClass);
    }

    private void updateExitButton() {
        //    UserDetails userDetails = securityService.getAuthenticatedUser().get();
        //    logoutMenuItem.setText(String.format("%s (Выход)", userDetails.getUsername()));
        User currentUser = userService.getCurrentUser();
        logoutMenuItem.setText(String.format("%s (Выход)", currentUser.toString()));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        updateExitButton();
    }

}

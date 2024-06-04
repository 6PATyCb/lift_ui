/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.vaadin;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import java.time.LocalDateTime;
import ru.java_inside.lift_ui.LiftUiException;
import ru.java_inside.lift_ui.utils.DateUtils;

public final class VaadinUtils {

    private VaadinUtils() {
    }

    public static Button createExcelButton() {
        return new Button("Excel");
    }

    public static Button createHelpButton(String helpText) {
        Button button = new Button(VaadinIcon.QUESTION.create());
        button.setTooltipText(helpText);
        return button;
    }

//    public static H1 createVersionComponent(Clock clock, BuildProperties buildProperties, String springProfile) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withZone(clock.getZone());
//        String dateStr = formatter.format(buildProperties.getTime());
//        var version = new H1(String.format("v%s %s", buildProperties.getVersion(), springProfile));
//        version.setTitle(String.format("Версия %s от %s профиль %s", buildProperties.getVersion(), dateStr, springProfile));
//        version.addClassNames(
//                LumoUtility.FontSize.SMALL,
//                LumoUtility.Margin.MEDIUM);
//        return version;
//    }
    public static void showErrorNotification(String msg, Throwable ex) {
        if (ex != null) {
            if (!(ex instanceof LiftUiException)) {
                ex.printStackTrace(System.out);
            }
        }
        VaadinUtils.showErrorNotification(msg);
    }

    public static void showErrorLongNotification(String msg, Throwable ex) {
        if (ex != null) {
            if (!(ex instanceof LiftUiException)) {
                ex.printStackTrace(System.out);
            }
        }
        VaadinUtils.showErrorLongNotification(msg);
    }

    public static void showSuccessLongNotification(String msg) {
        showSuccessNotification(msg, -1);
    }

    public static void showSuccessNotification(String msg) {
        showSuccessNotification(msg, 3000);
    }

    public static void showSuccessNotification(String msg, int duration) {
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        Div text = new Div(new Text(msg));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> notification.close());
        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.getElement().addEventListener("click", event -> notification.close());
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        notification.add(layout);
        notification.setDuration(duration);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.open();
    }

    public static void showTrayNotification(String msg) {
        showTrayNotification(msg, 3000);
    }

    public static void showTrayNotification(String msg, int duration) {
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);

        Div text = new Div(new Text(msg));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> notification.close());
        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.getElement().addEventListener("click", event -> notification.close());
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        notification.add(layout);
        notification.setDuration(duration);
        notification.setPosition(Notification.Position.BOTTOM_END);
        notification.open();
    }

    public static void showErrorLongNotification(String msg) {
        showErrorNotification(msg, -1);
    }

    public static void showErrorNotification(String msg) {
        showErrorNotification(msg, 3000);
    }

    public static void showErrorNotification(String msg, int duration) {
        LocalDateTime now = LocalDateTime.now(); //специально берем системные часы, чтобы по логу искать именно это же время
        String nowStr = now.format(DateUtils.APP_DATE_TIME_FORMATTER);
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Div text = new Div(new VerticalLayout(new Text(String.format("[%s]", nowStr)), new Text(msg)));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> notification.close());

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.getElement().addEventListener("click", event -> notification.close());
        notification.getElement().addEventListener("click", event -> notification.close());
        layout.setMaxWidth(90, Unit.VW);
        notification.add(layout);
        notification.setDuration(duration);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.open();
    }

    /**
     * Проверяем разрешения. логика взята с
     * https://vaadin.com/docs/latest/security/enabling-security#annotation-inheritance-and-overrides
     *
     * @param securityService
     * @param viewClass
     * @return
     */
    public static boolean checkSecurityView(
            // SecurityService securityService,
            Class viewClass) {
        boolean allow = false;
        PermitAll permitAll = (PermitAll) viewClass.getAnnotation(PermitAll.class);
        if (permitAll != null) {
            allow = true;
        }
//        RolesAllowed rolesAllowed = (RolesAllowed) viewClass.getAnnotation(RolesAllowed.class);
//        if (rolesAllowed != null) {
//            Optional<UserDetails> userDetailOptional = securityService.getAuthenticatedUser();
//            allow = false;
//            if (userDetailOptional.isPresent()) {
//                for (String role : rolesAllowed.value()) {
//                    boolean contains = Roles.isContainsRole(userDetailOptional.get(), role);
//                    if (contains) {
//                        allow = true;
//                        break;
//                    }
//                }
//            }
//        }
        AnonymousAllowed anonymousAllowed = (AnonymousAllowed) viewClass.getAnnotation(AnonymousAllowed.class);
        if (anonymousAllowed != null) {
            allow = true;
        }
        DenyAll denyAll = (DenyAll) viewClass.getAnnotation(DenyAll.class);
        if (denyAll != null) {
            allow = false;
        }
        return allow;
    }

    public static void handleValidationException(ValidationException e) {
        for (ValidationResult validationResult : e.getValidationErrors()) {
            System.out.println("!!" + validationResult);
            if (validationResult.isError()) {
                throw new LiftUiException(validationResult.getErrorMessage());
            }
        }
    }
}

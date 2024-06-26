/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.vaadin.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import jakarta.annotation.security.PermitAll;
import java.util.Arrays;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import ru.java_inside.lift_ui.LiftUiException;
import ru.java_inside.lift_ui.lift.LiftAction;
import ru.java_inside.lift_ui.lift.LiftActionsGraph;
import ru.java_inside.lift_ui.lift.LiftService;
import ru.java_inside.lift_ui.lift.LiftState;
import ru.java_inside.lift_ui.lift.PassengerAction;
import ru.java_inside.lift_ui.users.Role;
import ru.java_inside.lift_ui.users.User;
import ru.java_inside.lift_ui.users.UserService;
import ru.java_inside.lift_ui.vaadin.LiftUiIcons;
import ru.java_inside.lift_ui.vaadin.VaadinUtils;
import ru.java_inside.lift_ui.vaadin.custom.Panel;

@PermitAll
@Route(value = "", layout = MainAppLayout.class)
@PageTitle("Управление лифтом")
public final class MainPageView extends VerticalLayout {

    public final static String PATH = "";
    private final UserService userService;
    private final LiftService liftService;
    private final ComboBox<Role> selectRoleComboBox = new ComboBox<>();
    private final Span roleSpan = new Span();
    private final IntegerField userfloorIntegerField = new IntegerField();
    private final IntegerField goTofloorIntegerField = new IntegerField();
    private final Button callFloorButton = new Button("вызвать на этаж", VaadinIcon.TOUCH.create());
    private final Button stepOnButton = new Button("войти в лифт", VaadinIcon.SIGN_IN.create());
    private final Button goToFloorButton = new Button("поехать на этаж", VaadinIcon.LIST_OL.create());
    private final Button repairButton = new Button("Починить", VaadinIcon.TOOLS.create());
    private final ProgressBar liftMovementProgressBar = new ProgressBar();

    private Registration pollerRegistration = null;

    private Dialog liftStatusDialog = null;
    /**
     * Флаг для определения, что я движусь в лифте. Используется, чтобы после
     * выхода из лифта мой текущий этаж менялся на тот на который я ехал
     */
    private boolean moveInside = false;

    public MainPageView(
            @Autowired UserService userService,
            @Autowired LiftService liftService
    ) {
        this.userService = userService;
        this.liftService = liftService;

        pollerRegistration = UI.getCurrent().addPollListener(e -> {
            LiftState liftState = liftService.getLiftState();
            updateButtonsAvailibility(liftState);
            updateLiftMovementProgressBar(liftState);
        });

        selectRoleComboBox.setItems(Arrays.asList(Role.values()));
        selectRoleComboBox.setItemLabelGenerator(Role::getName);
        selectRoleComboBox.addValueChangeListener(e -> {
            Role role = e.getValue();
            userService.switchCurrentRole(role);
            updateRoleDesc();
            UI.getCurrent().navigate(MainPageView.class);
        });
        selectRoleComboBox.setValue(userService.getCurrentUser().getRole());

        userfloorIntegerField.setValue(1);
        userfloorIntegerField.setMin(1);
        userfloorIntegerField.setMax(Byte.MAX_VALUE);
        userfloorIntegerField.setStepButtonsVisible(true);

        goTofloorIntegerField.setValue(1);
        goTofloorIntegerField.setMin(1);
        goTofloorIntegerField.setMax(Byte.MAX_VALUE);
        goTofloorIntegerField.setStepButtonsVisible(true);

        callFloorButton.addClickListener(ev -> {
            try {
                if (userfloorIntegerField.isInvalid()) {
                    throw new LiftUiException("Ошибка в заполнении полей");
                }
                byte floor = userfloorIntegerField.getValue().byteValue();
                liftService.call(floor);
                showLiftStateWindow();
                VaadinUtils.showTrayNotification(String.format("Вызов лифта на этаж %d прошел успешно", floor));
            } catch (RuntimeException e) {
                VaadinUtils.showErrorLongNotification(String.format("Произошла ошибка %s", e.toString()), e);
            }
        });
        stepOnButton.addClickListener(ev -> {
            try {
                User currentUser = userService.getCurrentUser();
                liftService.stepOn(currentUser);
                showLiftStateWindow();
                VaadinUtils.showTrayNotification(String.format("Человек вошел в лифт успешно"));
            } catch (RuntimeException e) {
                VaadinUtils.showErrorLongNotification(String.format("Произошла ошибка %s", e.toString()), e);
            }
        });
        goToFloorButton.addClickListener(ev -> {
            try {
                if (goTofloorIntegerField.isInvalid()) {
                    throw new LiftUiException("Ошибка в заполнении полей");
                }
                byte floor = goTofloorIntegerField.getValue().byteValue();
                User currentUser = userService.getCurrentUser();
                liftService.goToFloor(currentUser, floor);
                showLiftStateWindow();
                VaadinUtils.showTrayNotification(String.format("Начало поездки пассажира на этаж %d", floor));
            } catch (RuntimeException e) {
                VaadinUtils.showErrorLongNotification(String.format("Произошла ошибка %s", e.toString()), e);
            }
        });
        repairButton.setTooltipText("Только лифтер может чинить лифт находясь с ним на одном этаже");
        repairButton.addClickListener(ev -> {
            try {
                User currentUser = userService.getCurrentUser();
                liftService.repair(currentUser);
                showLiftStateWindow();
                VaadinUtils.showTrayNotification(String.format("Лифт успешно починен"));
            } catch (RuntimeException e) {
                VaadinUtils.showErrorLongNotification(String.format("Произошла ошибка %s", e.toString()), e);
            }
        });
    }

    private void updateRoleDesc() {
        Role role = selectRoleComboBox.getValue();
        roleSpan.setText(role.getDescription());
    }

    /**
     * Отображение плавающего окна с состоянием лифта
     */
    private void showLiftStateWindow() {
        if (liftStatusDialog != null) {
            return;
        }

        Span liftStateSpan = new Span();
        liftStateSpan.setText(liftService.getLiftState().lastStateMessage);

        Registration windowPollerRegistration = UI.getCurrent().addPollListener(e -> {
            LiftState liftState = liftService.getLiftState();
            liftStateSpan.setText(liftState.lastStateMessage);
            updateButtonsAvailibility(liftState);
        });

        VerticalLayout layoutV = new VerticalLayout();
        layoutV.add(liftStateSpan);

        liftStatusDialog = new Dialog("Состояние лифта", layoutV);
        liftStatusDialog.setModal(false);
        liftStatusDialog.setDraggable(true);
//        liftStatusDialog.addAttachListener((event) -> {
//       //     System.out.println("!!attached");
//        });
        liftStatusDialog.addDetachListener((event) -> {
            //    System.out.println("!!detached");
            windowPollerRegistration.remove();
        });
        Button closeWindowButton = new Button(LiftUiIcons.cancel(), e -> {
            liftStatusDialog.close();
            liftStatusDialog = null;
        });
        closeWindowButton.setTooltipText("Закрыть окно");
        layoutV.add(closeWindowButton);
        layoutV.setHorizontalComponentAlignment(Alignment.END, closeWindowButton);
        liftStatusDialog.open();
    }

    /**
     * Обновление состояний доступных кнопок в момент изменения состояния лифта
     *
     * @param currentAction
     */
    private void updateButtonsAvailibility(LiftState liftState) {
        LiftAction currentAction = liftState.action;
        User currentUser = userService.getCurrentUser();
        byte userFloor = userfloorIntegerField.getValue().byteValue();
        userfloorIntegerField.setEnabled(true);
        callFloorButton.setEnabled(false);
        stepOnButton.setEnabled(false);
        goTofloorIntegerField.setEnabled(false);
        goToFloorButton.setEnabled(false);
        repairButton.setEnabled(false);
        Set<PassengerAction> availablePassengerActions = LiftActionsGraph.getAvailableActions(currentAction);
        if (availablePassengerActions.contains(PassengerAction.CALL) && PassengerAction.CALL.isActionAvailable(currentUser, userFloor, liftState)) {
            callFloorButton.setEnabled(true);
        }
        if (availablePassengerActions.contains(PassengerAction.STEP_ON) && PassengerAction.STEP_ON.isActionAvailable(currentUser, userFloor, liftState)) {
            stepOnButton.setEnabled(true);
        }
        if (availablePassengerActions.contains(PassengerAction.GO_TO_FLOOR) && PassengerAction.GO_TO_FLOOR.isActionAvailable(currentUser, userFloor, liftState)) {
            userfloorIntegerField.setEnabled(false);
            goTofloorIntegerField.setEnabled(true);
            goToFloorButton.setEnabled(true);
        }
        if (availablePassengerActions.contains(PassengerAction.REPAIR) && PassengerAction.REPAIR.isActionAvailable(currentUser, userFloor, liftState)) {
            repairButton.setEnabled(true);
        }
        {//для обновления значения моего текущего этажа, когда я вышел из лифта
            if (!moveInside) {
                if (currentAction.isMovementAction() && userService.getCurrentUser().equals(liftState.user)) {
                    moveInside = true;
                }
            } else {
                if (liftState.user == null || !userService.getCurrentUser().equals(liftState.user)) {
                    userfloorIntegerField.setValue(Integer.valueOf(liftState.currentFloor));
                    moveInside = false;
                }
            }
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        HorizontalLayout userLayoutH = new HorizontalLayout(selectRoleComboBox, roleSpan, VaadinUtils.createHelpButton("Выбирай роль для своего пользователя"));
        userLayoutH.setSizeFull();
        userLayoutH.setAlignItems(Alignment.CENTER);

        Panel userPanel = new Panel("Пользователь");
        userPanel.add(userLayoutH);

        HorizontalLayout outsideLayoutH = new HorizontalLayout(
                new Span("Я на этаже:"), userfloorIntegerField,
                callFloorButton,
                stepOnButton,
                repairButton,
                VaadinUtils.createHelpButton("Выбирай этаж на котором ты находишься. Вызывай лифт и заходи в него. Если лифт сломался, то пользователь с ролью лифтера может его починить на том этаже, где он сломался")
        );
        outsideLayoutH.setAlignItems(Alignment.CENTER);

        Panel outsidePanel = new Panel("В подъезде");
        outsidePanel.add(outsideLayoutH);

        HorizontalLayout insideLiftLayoutH = new HorizontalLayout(
                new Span("Я еду на этаж:"), goTofloorIntegerField,
                goToFloorButton,
                VaadinUtils.createHelpButton("Выбирай этаж на который ты поедешь, если ты уже находишься в лифте")
        );
        insideLiftLayoutH.setAlignItems(Alignment.CENTER);

        VerticalLayout insideLiftLayoutV = new VerticalLayout(insideLiftLayoutH, liftMovementProgressBar);

        Panel insidePanel = new Panel("В лифте");
        insidePanel.add(insideLiftLayoutV);

        add(userPanel);
        add(new Hr());
        add(outsidePanel);
        add(insidePanel);

        UI.getCurrent().setPollInterval(500);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        if (pollerRegistration != null) {
            pollerRegistration.remove();
        }
        if (liftStatusDialog != null) {
            liftStatusDialog.close();
        }
        UI.getCurrent().setPollInterval(-1);
    }

    /**
     * Обновление прогрессбара лифта.
     *
     * @param liftState
     */
    private void updateLiftMovementProgressBar(LiftState liftState) {
        if (liftState.action != null && liftState.action.isMovementAction()) {

            if (userfloorIntegerField.getValue().byteValue() == liftState.waitFloor || userService.getCurrentUser().equals(liftState.user)) {//если лифт едет на мой этаж или я еду в лифте
                liftMovementProgressBar.setIndeterminate(false);
                byte min = liftState.lastHoldFloor;
                byte max = liftState.waitFloor;
                byte curr = liftState.currentFloor;
                if (max < min) {
                    byte tmp = max;
                    max = min;
                    min = tmp;
                    curr = (byte) (max - curr + min);
                }
                liftMovementProgressBar.setMin(min);
                liftMovementProgressBar.setMax(max);
                liftMovementProgressBar.setValue(curr);
            } else {
                liftMovementProgressBar.setIndeterminate(true);
            }
        } else {
            liftMovementProgressBar.setMin(0);
            liftMovementProgressBar.setMax(1);
            liftMovementProgressBar.setValue(0);
            liftMovementProgressBar.setIndeterminate(false);
        }

    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.vaadin.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import ru.java_inside.lift_ui.lift.LiftService;
import ru.java_inside.lift_ui.lift.LiftState;

/**
 *
 * @author 6PATyCb
 */
@Route(value = "lift_state", layout = MainAppLayout.class)
@PageTitle("Состояние лифта")
public class LiftStateView extends VerticalLayout {

    private final Span liftFullStateSpan = new Span();
    private final Span currentFloorSpan = new Span();
    private final Span lastHoldFloorrSpan = new Span();
    private final Span waitFloorSpan = new Span();
    private final Span passangerSpan = new Span();
    private final Span lastMsgSpan = new Span();
    private final Span currentActionSpan = new Span();
    private final Span brokenSpan = new Span();
    private final LiftService liftService;
    private Registration pollerRegistration = null;

    public LiftStateView(
            @Autowired LiftService liftService
    ) {
        this.liftService = liftService;
        add(liftFullStateSpan);
        add(currentFloorSpan);
        add(lastHoldFloorrSpan);
        add(waitFloorSpan);
        add(passangerSpan);
        add(lastMsgSpan);
        add(currentActionSpan);
        add(brokenSpan);
        doPoll();
        pollerRegistration = UI.getCurrent().addPollListener(e -> doPoll());
    }

    private void doPoll() {
        {//считаем liftService
            LiftState liftState = liftService.getLiftState();
            liftFullStateSpan.setText(String.format("Полное состояние: %s", liftState.toString()));
            currentFloorSpan.setText(String.format("Текущий этаж: %d", liftState.currentFloor));
            lastHoldFloorrSpan.setText(String.format("Этаж последней остановки: %d", liftState.lastHoldFloor));
            waitFloorSpan.setText(String.format("Этаж на который направляется лифт: %d", liftState.waitFloor));
            passangerSpan.setText(String.format("Пассажир: %s", liftState.user == null ? "Нет" : liftState.user));
            lastMsgSpan.setText(String.format("Последнее сообщение: %s", liftState.lastStateMessage));
            currentActionSpan.setText(String.format("Текущее действие: %s", liftState.action));
            brokenSpan.setText(liftState.broken ? "Состояние: Сломан" : "Состояние: Работает");
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        UI.getCurrent().setPollInterval(500);
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        if (pollerRegistration != null) {
            pollerRegistration.remove();
        }
        UI.getCurrent().setPollInterval(-1);
    }

}

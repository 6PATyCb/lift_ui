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
import ru.java_inside.lift_ui.session.SessionService;

/**
 *
 * @author 6PATyCb
 */
@Route(value = "lift_state", layout = MainAppLayout.class)
@PageTitle("Состояние лифта")
public class LiftStateView extends VerticalLayout {

    private final Span sessionsCountSpan = new Span();
    private final Span liftStateSpan = new Span();
    private final LiftService liftService;
    private final SessionService sessionService;
    private Registration pollerRegistration = null;

    public LiftStateView(
            @Autowired LiftService liftService,
            @Autowired SessionService sessionService
    ) {
        this.liftService = liftService;
        this.sessionService = sessionService;
        add(sessionsCountSpan);
        add(liftStateSpan);
        doPoll();
        pollerRegistration = UI.getCurrent().addPollListener(e -> doPoll());
    }

    private void doPoll() {
        {//обновим количество сессий
            sessionsCountSpan.setText(String.format("Сессий: %d", sessionService.getSessionsCount()));
        }
        {//считаем liftService
            LiftState liftState = liftService.getLiftState();
            liftStateSpan.setText(liftState.toString());
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

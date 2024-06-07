/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.vaadin.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import ru.java_inside.lift_ui.lift.lift_ride.LiftRide;
import ru.java_inside.lift_ui.lift.lift_ride.LiftRideService;
import ru.java_inside.lift_ui.vaadin.LiftUiIcons;

/**
 *
 * @author 6PATyCb
 */
@Route(value = "lift_rides", layout = MainAppLayout.class)
@PageTitle("История поездок на лифте")
public class LiftRideListView extends VerticalLayout {

    private final Grid<LiftRideWrapper> grid = new Grid<>();
    private final Span countSpan = new Span();
    private final Clock clock;
    private final LiftRideService liftRideService;

    public LiftRideListView(
            @Autowired LiftRideService liftRideService,
            @Autowired Clock clock
    ) {
        this.liftRideService = liftRideService;
        this.clock = clock;

        grid.addColumn(LiftRideWrapper::getUserId)
                .setHeader("Id пользователя")
                .setSortable(true);
        grid.addColumn(LiftRideWrapper::getRidedFloors)
                .setHeader("Этажей проехал")
                .setSortable(true);
        grid.addColumn(new LocalDateTimeRenderer<>(LiftRideWrapper::getCreated, "dd-MM-yyyy HH:mm:ss"))
       // grid.addColumn(LiftRideWrapper::getCreated)
                .setHeader("Когда")
                .setSortable(true);

        update();
        add(new Button("Обновить", LiftUiIcons.refresh(), e -> {
            update();
        }));
        add(grid);
        add(countSpan);
        setSizeFull();
    }

    private void update() {
        List<LiftRideWrapper> rows = liftRideService.getLast100LiftRides().stream()
                .map(LiftRideWrapper::new)
                .collect(Collectors.toList());
        grid.setItems(rows);
        countSpan.setText(String.format("Записей: %d", rows.size()));
    }

    private class LiftRideWrapper {

        private final LiftRide liftRide;
        private final LocalDateTime created;

        public LiftRideWrapper(LiftRide liftRide) {
            this.liftRide = liftRide;
            created = LocalDateTime.ofInstant(Instant.ofEpochMilli(liftRide.getTimestamp()), clock.getZone());
        }

        public String getUserId() {
            return liftRide.getUserId();
        }

        public int getRidedFloors() {
            return liftRide.getRidedFloors();
        }

        public LocalDateTime getCreated() {
            return created;
        }

    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.vaadin.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.java_inside.lift_ui.users.names.Name;
import ru.java_inside.lift_ui.users.names.NamesService;
import ru.java_inside.lift_ui.vaadin.LiftUiIcons;
import ru.java_inside.lift_ui.vaadin.VaadinUtils;

/**
 *
 * @author 6PATyCb
 */
@Route(value = "names_editor", layout = MainAppLayout.class)
@PageTitle("Редактор имен пользователей")
public class NamesEditorView extends VerticalLayout {

    private final Grid<NameWrapper> grid = new Grid<>();
    private final Button deleteButton = new Button("Удалить", LiftUiIcons.delete());
    private final Span countSpan = new Span();
    private final NamesService namesService;

    public NamesEditorView(
            @Autowired NamesService namesService
    ) {
        this.namesService = namesService;

        grid.addColumn(NameWrapper::getId)
                .setHeader("Id")
                .setSortable(true).setResizable(true);
        grid.addColumn(NameWrapper::getName)
                .setHeader("Имя")
                .setSortable(true).setResizable(true);
        grid.addColumn(new ComponentRenderer<>(nameWrapper -> {
            if (!nameWrapper.isFemale()) {
                Span span = new Span(createIcon(VaadinIcon.MALE),
                        new Span("Муж"));
                span.getElement().getThemeList().add("badge");
                return span;
            } else {
                Span span = new Span(createIcon(VaadinIcon.FEMALE),
                        new Span("Жен"));
                span.getElement().getThemeList().add("badge_error");
                return VaadinIcon.FEMALE.create();
            }
        })).setHeader("Пол")
                .setSortable(true).setResizable(true);
        deleteButton.addClickListener(e -> {
            Set<NameWrapper> selectedItems = grid.getSelectedItems();
            if (selectedItems.isEmpty()) {
                return;
            }
            NameWrapper selectedNameWrapper = grid.getSelectedItems().iterator().next();
            if (selectedNameWrapper == null) {
                return;
            }
            ConfirmDialog confirmDialog = new ConfirmDialog(
                    "Подтверждение действия",
                    "Вы уверены, что хотите удалить эту запись?",
                    "Да",
                    (event) -> {
                        try {
                            namesService.deleteName(selectedNameWrapper.toName());
                            VaadinUtils.showTrayNotification(String.format("Запись успешно удалена"));
                        } catch (RuntimeException ex) {
                            VaadinUtils.showErrorLongNotification(String.format("Произошла ошибка %s", ex.toString()), ex);
                        }
                        update();
                    }, "Отмена",
                    (event) -> {
                        event.getSource().close();
                    });
            confirmDialog.open();
        });
        deleteButton.addClickShortcut(Key.DELETE);
        deleteButton.setTooltipText(String.format("Горячая клавиша DELETE"));
        update();

        HorizontalLayout actionsLayoutH = new HorizontalLayout();
        actionsLayoutH.add(deleteButton);
        actionsLayoutH.add(new Button("Обновить", LiftUiIcons.refresh(), e -> {
            update();
        }));
        add(actionsLayoutH);
        add(grid);
        add(countSpan);
        setSizeFull();
    }

    private void update() {
        List<NameWrapper> rows = namesService.getAllNames().stream()
                .map(NameWrapper::new)
                .collect(Collectors.toList());
        grid.setItems(rows);
        countSpan.setText(String.format("Записей: %d", rows.size()));
    }

    @Getter
    @Setter
    private class NameWrapper {

        private int id;
        private String name;
        private boolean female;

        public NameWrapper(Name name) {
            id = name.getId();
            this.name = name.getName();
            female = name.isFemale();
        }

        public Name toName() {
            return new Name(id, name, female);
        }

    }

    private Icon createIcon(VaadinIcon vaadinIcon) {
        Icon icon = vaadinIcon.create();
        icon.getStyle().set("padding", "var(--lumo-space-xs)");
        return icon;
    }

}

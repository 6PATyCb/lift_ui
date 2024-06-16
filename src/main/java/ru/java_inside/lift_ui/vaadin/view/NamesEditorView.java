/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.vaadin.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import java.util.Collections;
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
    private final Button addButton = new Button("Создать", LiftUiIcons.add());
    private final Button editButton = new Button("Редактировать", LiftUiIcons.edit());
    private final Button deleteButton = new Button("Удалить", LiftUiIcons.delete());
    private final Button refreshButton = new Button("Обновить", LiftUiIcons.refresh(), e -> refreshGrid());
    private final Span countSpan = new Span();
    private final ComboBox<Grid.SelectionMode> gridSelectionModeComboBox = new ComboBox<>();
    private final NamesService namesService;
    private Registration selectionListenerReg;

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
        grid.addColumn(new ComponentRenderer<>(NameWrapper::sexToBadge)).setHeader("Пол")
                .setSortable(true).setResizable(true);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        selectionListenerReg = grid.addSelectionListener(e -> refreshButtons(e.getAllSelectedItems()));
        gridSelectionModeComboBox.setItems(Grid.SelectionMode.SINGLE, Grid.SelectionMode.MULTI);
        gridSelectionModeComboBox.setItemLabelGenerator(sm -> {
            return sm == Grid.SelectionMode.SINGLE ? "Одна строка" : "Несколько строк";
        });
        gridSelectionModeComboBox.setTooltipText("Режим выделения строк");
        gridSelectionModeComboBox.setValue(Grid.SelectionMode.SINGLE);
        gridSelectionModeComboBox.addValueChangeListener(ev -> {
            selectionListenerReg.remove();
            grid.setSelectionMode(ev.getValue());
            selectionListenerReg = grid.addSelectionListener(e -> refreshButtons(e.getAllSelectedItems()));
            refreshButtons(Collections.EMPTY_SET);
        });

        deleteButton.addClickListener(e -> {
            List<Name> selectedNames = getSelectedNames();
            if (selectedNames == null) {
                return;
            }
            int selectedSize = selectedNames.size();
            ConfirmDialog confirmDialog = new ConfirmDialog(
                    "Подтверждение действия",
                    selectedSize == 1 ? "Вы уверены, что хотите удалить эту запись?" : "Вы уверены, что хотите удалить выбранные записи?",
                    "Да",
                    (event) -> {
                        try {
                            namesService.deleteNames(selectedNames);
                            VaadinUtils.showTrayNotification(selectedSize == 1 ? String.format("Запись успешно удалена") : String.format("Записей успешно удалено: %d", selectedSize));
                        } catch (RuntimeException ex) {
                            VaadinUtils.showErrorLongNotification(String.format("Произошла ошибка %s", ex.toString()), ex);
                        }
                        refreshGrid();
                    }, "Отмена",
                    (event) -> {
                        event.getSource().close();
                    });
            confirmDialog.open();
        });
        deleteButton.addClickShortcut(Key.DELETE);
        deleteButton.setTooltipText(String.format("Горячая клавиша DELETE"));

        addButton.addClickListener(e -> {
            NamesEditForm editForm = new NamesEditForm(new NameWrapper(new Name("", false)));
            Dialog dialog = new Dialog("Создание имени", editForm);
            dialog.setCloseOnOutsideClick(false);
            dialog.setDraggable(true);
            dialog.getFooter().add(new Button("Отмена", LiftUiIcons.cancel(), ev -> dialog.close()));
            dialog.getFooter().add(new Button("Сохранить", LiftUiIcons.save(), ev -> {
                try {
                    NameWrapper changedNameWrapper = editForm.getValue();
                    namesService.saveName(changedNameWrapper.toName());
                    VaadinUtils.showTrayNotification(String.format("Запись успешно создана"));
                    dialog.close();
                } catch (RuntimeException ex) {
                    VaadinUtils.showErrorLongNotification(String.format("Произошла ошибка %s", ex.toString()), ex);
                }
                refreshGrid();
            }));
            dialog.open();
        });
        addButton.addClickShortcut(Key.KEY_C);
        addButton.setTooltipText(String.format("Горячая клавиша C"));

        editButton.addClickListener(e -> {
            NameWrapper selectedNameWrapper = getSelectedNameWrapper();
            if (selectedNameWrapper == null) {
                return;
            }
            NamesEditForm editForm = new NamesEditForm(selectedNameWrapper);
            Dialog dialog = new Dialog("Редактирование имени", editForm);
            dialog.setCloseOnOutsideClick(false);
            dialog.setDraggable(true);
            dialog.getFooter().add(new Button("Отмена", LiftUiIcons.cancel(), ev -> dialog.close()));
            dialog.getFooter().add(new Button("Сохранить", LiftUiIcons.save(), ev -> {
                try {
                    NameWrapper changedNameWrapper = editForm.getValue();
                    namesService.saveName(changedNameWrapper.toName());
                    VaadinUtils.showTrayNotification(String.format("Запись успешно изменена"));
                    dialog.close();
                } catch (RuntimeException ex) {
                    VaadinUtils.showErrorLongNotification(String.format("Произошла ошибка %s", ex.toString()), ex);
                }
                refreshGrid();
            }));
            dialog.open();
        });
        editButton.addClickShortcut(Key.KEY_E);
        editButton.setTooltipText(String.format("Горячая клавиша E"));

        refreshButton.addClickShortcut(Key.KEY_R);
        refreshButton.setTooltipText(String.format("Горячая клавиша R"));

        HorizontalLayout actionsLayoutH = new HorizontalLayout();
        actionsLayoutH.add(addButton);
        actionsLayoutH.add(editButton);
        actionsLayoutH.add(deleteButton);
        actionsLayoutH.add(refreshButton);
        actionsLayoutH.add(gridSelectionModeComboBox);
        actionsLayoutH.add(VaadinUtils.createHelpButton("Имя пользователя выбирается из этого списка случайно при каждом создании нового пользователя. Новый пользователь создается при первом открытии приложения или после выхода из него"));

        add(actionsLayoutH);
        add(grid);
        add(countSpan);
        setSizeFull();

        refreshGrid();
        refreshButtons(Collections.EMPTY_SET);
    }

    private void refreshButtons(Set<NameWrapper> nameWrappers) {
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        if (nameWrappers.size() == 1) {
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
            return;
        }
        if (nameWrappers.size() > 1) {
            deleteButton.setEnabled(true);
        }
    }

    /**
     * Получение выделенной строки из грида
     *
     * @return
     */
    private NameWrapper getSelectedNameWrapper() {
        Set<NameWrapper> selectedItems = grid.getSelectedItems();
        if (selectedItems.isEmpty()) {
            return null;
        }
        return grid.getSelectedItems().iterator().next();
    }

    /**
     * Получение выделенных строк из грида
     *
     * @return
     */
    private List<Name> getSelectedNames() {
        Set<NameWrapper> selectedItems = grid.getSelectedItems();
        if (selectedItems.isEmpty()) {
            return null;
        }
        return selectedItems.stream().map(NameWrapper::toName).collect(Collectors.toList());
    }

    /**
     * Обновление грида
     */
    private void refreshGrid() {
        List<NameWrapper> rows = namesService.getAllNames().stream()
                .map(NameWrapper::new)
                .collect(Collectors.toList());
        grid.setItems(rows);
        countSpan.setText(String.format("Записей: %d", rows.size()));
    }

    @Getter
    @Setter
    public static class NameWrapper {

        private final int id;
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

        public Span sexToBadge() {
            return sexToBadge(female);
        }

        public static Span sexToBadge(boolean sex) {
            if (!sex) {
                Span span = new Span(createIcon(VaadinIcon.MALE),
                        new Span("Муж"));
                span.getElement().getThemeList().add("badge");
                return span;
            } else {
                Span span = new Span(createIcon(VaadinIcon.FEMALE),
                        new Span("Жен"));
                span.getElement().getThemeList().add("badge error");
                return span;
            }

        }

        /**
         * Создание иконки для столбца пола
         *
         * @param vaadinIcon
         * @return
         */
        private static Icon createIcon(VaadinIcon vaadinIcon) {
            Icon icon = vaadinIcon.create();
            icon.getStyle().set("padding", "var(--lumo-space-xs)");
            return icon;
        }
    }

}

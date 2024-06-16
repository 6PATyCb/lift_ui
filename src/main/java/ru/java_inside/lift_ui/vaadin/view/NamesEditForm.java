/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.vaadin.view;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import ru.java_inside.lift_ui.LiftUiException;
import ru.java_inside.lift_ui.vaadin.view.NamesEditorView.NameWrapper;

/**
 *
 * @author 6PATyCb
 */
public class NamesEditForm extends VerticalLayout {

    private final IntegerField id = new IntegerField("Id");
    private final TextField name = new TextField("Имя");
    private final ComboBox<Boolean> female = new ComboBox<>("Пол");
    private final Binder<NameWrapper> binder = new Binder<>(NameWrapper.class);

    public NamesEditForm(NameWrapper nameWrapper) {

        female.setItems(Boolean.FALSE, Boolean.TRUE);
        female.setItemLabelGenerator(val -> val ? "Жен" : "Муж");

        add(id);
        add(name);
        add(female);

        binder.bindInstanceFields(this);//автоматически маппит поля формы с полями бина
        binder.forField(name)//докидаем валидаторы
                .withValidator(value -> value.length() >= 2 && value.length() <= 64, "Имя должно быть в диапазоне от 2 до 64 символов")
                .withValidator(value -> value.matches("^[А-Яа-яёЁ]+$"), "Имя должно содержать только русские буквы (А-Яа-я)")
                .asRequired()
                .bind(NameWrapper::getName, NameWrapper::setName);

        binder.setBean(nameWrapper);
        if (nameWrapper.getId() <= 0) {//для новой записи не будем отображать поле id
            id.setVisible(false);
        }
    }

    /**
     * Получение бина с формы. Будет выброшено исключение, если какие-то из
     * валидаторов вернули ошибку
     *
     * @return
     */
    public NameWrapper getValue() {
        if (!binder.isValid()) {
            throw new LiftUiException("Исправьте все ошибки в полях");
        }
        return binder.getBean();
    }

}

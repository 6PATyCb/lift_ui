/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.vaadin.custom;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;

/**
 *
 * @author 6PATyCb
 */
public class Panel extends Div {

    private static final String AS_PANEL = "as_panel";
    private Span textSpan;

    public Panel() {
        addClassName(AS_PANEL);
    }

    public Panel(Component... components) {
        super(components);
        addClassName(AS_PANEL);
    }

    public Panel(String text) {
        addClassName(AS_PANEL);
        textSpan = createTextSpan(text);
        add(textSpan);
    }

    @Override
    public void setText(String text) {
        if (textSpan == null) {
            textSpan = createTextSpan(text);
            addComponentAsFirst(textSpan);
        }
        if (text == null || text.isEmpty()) {
            remove(textSpan);
            textSpan = null;
            return;
        }
        textSpan.setText(text);
    }

    @Override
    public String getText() {
        if (textSpan == null) {
            return null;
        }
        return textSpan.getText();
    }

    private Span createTextSpan(String text) {
        Span span = new Span(text);
        span.getStyle().set("font-size", "8pt");
        span.getStyle().set("position", "relative");
        span.getStyle().set("top", "-25px");
        span.getStyle().set("left", "-13px");
        return span;
    }

}

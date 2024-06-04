package ru.java_inside.lift_ui.vaadin;

import com.vaadin.flow.i18n.I18NProvider;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Component;
import ru.java_inside.lift_ui.LiftUiApplication;

/**
 * Simple implementation of {@link I18NProvider}.
 */
@Component
public class SimpleI18NProvider implements I18NProvider {

    @Override
    public List<Locale> getProvidedLocales() {
        //System.out.println("!!!getProvidedLocales");
        return Collections.unmodifiableList(
                Arrays.asList(LiftUiApplication.LOCALE, Locale.ENGLISH));
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        //   System.out.println("!!!getTranslation");
        if (Locale.ENGLISH.equals(locale)) {
            if (key.equals("root.navigate_to_component")) {
                return "Navigate to another component";
            }
        } else if (LiftUiApplication.LOCALE.equals(locale)) {
            if (key.equals("root.navigate_to_component")) {
                return "前往另一视图123йцукен";
            }
        }
        return null;
    }

}

package ru.java_inside.lift_ui;

import java.util.Locale;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiftUiApplication {

    public static final Locale LOCALE = new Locale("ru", "RU");

    public static void main(String[] args) {
        SpringApplication.run(LiftUiApplication.class, args);
    }

}

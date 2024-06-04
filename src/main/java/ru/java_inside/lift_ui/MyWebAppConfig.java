/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author 6PATyCb
 */
@Configuration
@PropertySource(ignoreResourceNotFound = false, value = "classpath:application.properties")
@ComponentScan("ru.java_inside.lift_ui.*")
@EnableScheduling
public class MyWebAppConfig {

    public static final String PROFILE = "${spring.profiles.active:default}";

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}

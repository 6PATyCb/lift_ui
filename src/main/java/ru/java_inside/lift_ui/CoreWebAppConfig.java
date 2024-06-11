package ru.java_inside.lift_ui;

import java.time.Clock;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.java_inside.lift_ui.lift.lift_ride.LiftRideDaoImpl;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author 6PATyCb
 */
@Configuration
@EnableCaching
public class CoreWebAppConfig {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(LiftRideDaoImpl.CACHE_NAME);
    }

}

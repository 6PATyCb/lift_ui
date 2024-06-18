/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.lift.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * Сервис для обработки событий аудита. Пока просто логируем
 *
 * @author 6PATyCb
 */
@Slf4j
@Service
public class AuditServiceImpl implements AuditService, ApplicationListener<AuditedEvent> {

    @Override
    public void onApplicationEvent(AuditedEvent event) {
        log.info("Получено событие аудита: " + event);
    }

}

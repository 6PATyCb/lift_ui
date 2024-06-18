/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui.lift.audit;

import java.time.Clock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.java_inside.lift_ui.SpringProxyOrder;
import ru.java_inside.lift_ui.users.User;
import ru.java_inside.lift_ui.vaadin.LiftUserFilter;

/**
 * Прокси для аудита событий по изменению, удалению данных в спринговых бинах.
 * Позволяет аннотировать нужные для аудита методы аннотациями SaveAudited и
 * DeleteAudited.
 *
 * @author 6PATyCb
 */
@Aspect
@Order(SpringProxyOrder.AUDIT_ORDER)
@Component
public class AuditProxyComponent {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private LiftUserFilter liftUserFilter;

    private Clock clock;

    /**
     * Создание прокси для метода, который что-то сохраняет, чтобы в дальнейшем
     * иметь возможность работать с такими событиями
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("@annotation(SaveAudited)")
    public Object doMethodSaveAudit(ProceedingJoinPoint pjp) throws Throwable {
        return doMethodAudit(pjp, true);
    }

    /**
     * Создание прокси для метода, который что-то удаляет, чтобы в дальнейшем
     * иметь возможность работать с такими событиями
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("@annotation(DeleteAudited)")
    public Object doMethodDeleteAudit(ProceedingJoinPoint pjp) throws Throwable {
        return doMethodAudit(pjp, false);
    }

    private Object doMethodAudit(ProceedingJoinPoint pjp, boolean save) throws Throwable {
        try {
            Object obj = pjp.proceed();
            {//фиксируем только методы, которые отработали без выбрасывания исключений
//                if (save) {
//                    System.out.println("!!doMethodSaveAudit " + pjp.getSignature().getName());
//                } else {
//                    System.out.println("!!doMethodDeleteAudit " + pjp.getSignature().getName());
//                }
//                System.out.println(Arrays.toString(pjp.getArgs()));
                User user = liftUserFilter.getUser();
                applicationEventPublisher.publishEvent(
                        new AuditedEvent(
                                save,
                                user,
                                pjp.getTarget().getClass(),
                                pjp.getSignature().getName(),
                                pjp.getArgs(),
                                this,
                                clock
                        )
                );
            }
            return obj;
        } catch (Throwable e) {
            throw e;
        }
    }

    @Autowired
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    @Autowired
    public void setLiftUserFilter(LiftUserFilter liftUserFilter) {
        this.liftUserFilter = liftUserFilter;
    }

}

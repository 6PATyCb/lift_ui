package ru.java_inside.lift_ui.lift.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для пометки методов удаления данных по которым нужно инициировать
 * события для дальнейшей возможности сохранять эту информацию
 *
 * @author 6PATyCb
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DeleteAudited {

}

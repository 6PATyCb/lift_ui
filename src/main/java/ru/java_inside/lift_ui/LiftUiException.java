/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.java_inside.lift_ui;

/**
 *
 * @author 6PATyCb
 */
public class LiftUiException extends RuntimeException {

    public LiftUiException() {
    }

    public LiftUiException(String message) {
        super(message);
    }

    public LiftUiException(String message, Throwable cause) {
        super(message, cause);
    }

    public LiftUiException(Throwable cause) {
        super(cause);
    }

    public LiftUiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

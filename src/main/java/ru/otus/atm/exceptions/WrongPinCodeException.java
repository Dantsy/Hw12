package ru.otus.atm.exceptions;

public class WrongPinCodeException extends Exception {
    public WrongPinCodeException(String message) {
        super(message);
    }
}

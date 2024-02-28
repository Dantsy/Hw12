package ru.otus.atm.exceptions;

public class WrongDenominationException extends Exception {
    public WrongDenominationException(String message) {
        super(message);
    }
}

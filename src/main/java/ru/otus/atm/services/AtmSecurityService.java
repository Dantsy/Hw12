package ru.otus.atm.services;

public interface AtmSecurityService {

    void setPinCode(String newPinCode);

    boolean isPinCorrect(String pinCode);
}

package ru.otus.atm.services;

import ru.otus.atm.exceptions.WrongAmountException;
import ru.otus.atm.exceptions.WrongDenominationException;
import ru.otus.atm.exceptions.WrongPinCodeException;

public interface AtmCommandService {
    String getMoney(String pinCode, long amount) throws WrongPinCodeException, WrongAmountException;

    String depositCash(String pinCode, Long denomination, Long amount) throws WrongPinCodeException, WrongDenominationException;

    String getBalance(String pinCode) throws WrongPinCodeException;

    String changePin(String pinCode, String newPinCode) throws WrongPinCodeException;
}

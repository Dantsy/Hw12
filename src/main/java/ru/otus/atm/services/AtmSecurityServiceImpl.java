package ru.otus.atm.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AtmSecurityServiceImpl implements AtmSecurityService {

    private String pinCode;

    public AtmSecurityServiceImpl(@Value("${service.atm-pin-code-default}") String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public void setPinCode(String newPinCode) {
        this.pinCode = newPinCode;
    }

    @Override
    public boolean isPinCorrect(String pinCode) {
        return this.pinCode.equals(pinCode);
    }
}

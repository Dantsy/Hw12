package ru.otus.atm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.atm.domain.BanknoteStack;
import ru.otus.atm.domain.Money;
import ru.otus.atm.exceptions.WrongAmountException;
import ru.otus.atm.exceptions.WrongDenominationException;
import ru.otus.atm.exceptions.WrongPinCodeException;

import java.util.Arrays;
import java.util.List;

@Service
public class AtmCommandServiceImpl implements AtmCommandService {

    CashStorageService cashStorageService;

    AtmSecurityService securityService;

    private final String WRONG_PING_CODE_MESSAGE = "You entered a wrong PIN code";

    public AtmCommandServiceImpl(@Autowired AtmSecurityService securityService,
                                 @Value("${service.banknote-denominations}") String denominationsString,
                                 @Value("${service.banknote-quantities}") String quantitiesString,
                                 @Value("${service.banknote-currency}") String currency) {

        List<Long> denominations = Arrays.stream(denominationsString.split(",")).map(Long::parseLong).toList();
        List<Long> quantities = Arrays.stream(quantitiesString.split(",")).map(Long::parseLong).toList();

        this.cashStorageService = new CashStorageServiceImpl(new Money(currency, denominations));

        BanknoteStack banknoteStack = new BanknoteStack();
        for (int i = 0; i < quantities.size(); i++) {
            banknoteStack.putBanknote(denominations.get(i), quantities.get(i));
        }

        cashStorageService.putBanknoteStack(banknoteStack);
        this.securityService = securityService;
    }

    @Override
    public String getMoney(String pinCode, long amount) throws WrongPinCodeException, WrongAmountException {

        if (!securityService.isPinCorrect(pinCode)) {
            throw new WrongPinCodeException(WRONG_PING_CODE_MESSAGE);
        }
        if (amount > cashStorageService.getTotalBalance()) {
            throw new WrongAmountException("Amount is greater than the balance");
        } else if (!cashStorageService.isAmountCanBeIssued(amount)) {
            throw new WrongAmountException("Entered amount cannot be issued");
        } else if (amount != 0) {
            BanknoteStack banknoteStackToIssue = cashStorageService.getBanknoteStackBySum(amount);
            cashStorageService.takeBanknoteStack(banknoteStackToIssue);
            return "You got: %s %s".formatted(banknoteStackToIssue, cashStorageService.getCurrency());
        } else {
            throw new WrongAmountException("You entered a wrong amount");
        }

    }

    @Override
    public String depositCash(String pinCode, Long denomination, Long amount) throws WrongPinCodeException, WrongDenominationException {

        if (!securityService.isPinCorrect(pinCode)) {
            throw new WrongPinCodeException(WRONG_PING_CODE_MESSAGE);
        }
        if (!cashStorageService.isDenominationExist(denomination)) {
            throw new WrongDenominationException("You entered a wrong denomination");
        }

        BanknoteStack banknoteStack = new BanknoteStack();
        banknoteStack.putBanknote(denomination, amount);
        cashStorageService.putBanknoteStack(banknoteStack);

        return "You put: %s %s".formatted(banknoteStack, cashStorageService.getCurrency());
    }

    @Override
    public String getBalance(String pinCode) throws WrongPinCodeException {
        if (!securityService.isPinCorrect(pinCode)) {
            throw new WrongPinCodeException(WRONG_PING_CODE_MESSAGE);
        }
        cashStorageService.getTotalBalance();
        return "Your account balance is: " + cashStorageService.getTotalBalance();
    }

    @Override
    public String changePin(String pinCode, String newPinCode) throws WrongPinCodeException {
        if (!securityService.isPinCorrect(pinCode)) {
            throw new WrongPinCodeException(WRONG_PING_CODE_MESSAGE);
        }
        securityService.setPinCode(newPinCode);
        return "PIN code changed successfully";
    }

}

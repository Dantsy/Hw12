package ru.otus.atm.services;

import ru.otus.atm.domain.BanknoteStack;

import java.util.List;

public interface CashStorageService {

    Long getTotalBalance();

    void putBanknoteStack(BanknoteStack banknoteStack);

    void takeBanknoteStack(BanknoteStack banknoteStack);

    Boolean isAmountCanBeIssued(Long amount);

    BanknoteStack getBanknoteStackBySum(Long amount);

    List<Long> getBanknoteDenominations();

    boolean isDenominationExist(Long denomination);

    String getCurrency();
}

package ru.otus.atm.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Money {

    private final List<Long> denominations = new ArrayList<>();
    private final String currency;

    public Money(String currency, List<Long> denominations) {
        this.currency = currency;
        this.denominations.addAll(denominations);
    }
}
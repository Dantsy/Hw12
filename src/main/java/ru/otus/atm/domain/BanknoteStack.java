package ru.otus.atm.domain;

import lombok.Getter;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class BanknoteStack {

    private final Map<Long, Long> banknotes = new TreeMap<>();


    public Long getTotalSum() {
        return banknotes.entrySet().stream().mapToLong(value -> value.getKey() * value.getValue()).sum();
    }

    public void addBanknoteStack(BanknoteStack banknoteStack) {
        banknoteStack.getBanknotes().forEach((key, value) -> banknotes.put(key, banknotes.getOrDefault(key, 0L) + value));
    }

    public void takeBanknoteStack(BanknoteStack banknoteStack) {
        banknoteStack.getBanknotes().forEach((key, value) -> banknotes.put(key, banknotes.getOrDefault(key, 0L) - value));
    }

    public BanknoteStack getBanknoteStackBySum(Long sum) {
        BanknoteStack result = new BanknoteStack();
        Long remainingSum = sum;

        for (Map.Entry<Long, Long> entry : getSortedBanknotesStream().toList()) {
            Long denomination = entry.getKey();
            Long count = entry.getValue();

            Long usedCount = Math.min(count, remainingSum / denomination);

            if (usedCount > 0) {
                result.getBanknotes().put(denomination, usedCount);
                remainingSum -= usedCount * denomination;
            }

            if (remainingSum == 0) {
                break;
            }
        }

        if (remainingSum > 0) {
            return new BanknoteStack();
        }

        takeBanknoteStack(result);

        return result;
    }

    public void putBanknote(Long value, Long amount) {
        banknotes.put(value, amount);
    }

    public Stream<Map.Entry<Long, Long>> getSortedBanknotesStream() {
        return banknotes.entrySet().stream().sorted((o1, o2) -> o2.getKey().intValue() - o1.getKey().intValue());
    }

    @Override
    public String toString() {
        return getSortedBanknotesStream().filter(tLongEntry -> tLongEntry.getValue() != 0)
                .map(tLongEntry -> tLongEntry.getKey() + "x" + tLongEntry.getValue()).collect(Collectors.joining(", "));
    }

}

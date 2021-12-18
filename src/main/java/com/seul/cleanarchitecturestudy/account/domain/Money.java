package com.seul.cleanarchitecturestudy.account.domain;

import lombok.Value;

import java.math.BigInteger;

@Value
public class Money {

    private final BigInteger value;

    private Money(BigInteger value) {
        this.value = value;
    }

    public static Money of(long value) {
        return new Money(BigInteger.valueOf(value));
    }

    public static Money add(Money money1, Money money2) {
        return new Money(money1.value.add(money2.value));
    }

    public Money negate() {
        return new Money(this.value.negate());
    }

    public boolean isPositive() {
        return this.value.compareTo(BigInteger.ZERO) > 0;
    }
}

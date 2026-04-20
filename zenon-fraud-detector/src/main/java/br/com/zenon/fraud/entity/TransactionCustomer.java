package br.com.zenon.fraud.entity;

import java.math.BigDecimal;
import java.util.Objects;

public record TransactionCustomer(
        String name,
        BigDecimal oldBalance,
        BigDecimal newBalance)
{
    public TransactionCustomer {
        Objects.requireNonNull(name);
        Objects.requireNonNull(oldBalance);
        Objects.requireNonNull(newBalance);

        if (name.trim().isEmpty()) throw new IllegalArgumentException("name shouldn't be null " + name);
        if (oldBalance.signum() < 0) throw new IllegalArgumentException("oldBalance should be positive " + oldBalance);
        if (newBalance.signum() < 0) throw new IllegalArgumentException("newBalance should be positive " + newBalance);
    }
}

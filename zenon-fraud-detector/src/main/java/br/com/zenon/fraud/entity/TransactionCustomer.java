package br.com.zenon.fraud.entity;

import java.math.BigDecimal;

public record TransactionCustomer(
        String name,
        BigDecimal oldbalance,
        BigDecimal newbalance) {
}

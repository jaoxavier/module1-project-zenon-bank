package br.com.zenon.fraud.entity;

import br.com.zenon.fraud.enums.TransactionType;

import java.math.BigDecimal;

public record Transaction(
        int step,
        TransactionType type,
        BigDecimal amount,

        TransactionCustomer origin,
        TransactionCustomer recipient,

        boolean isFraud,
        boolean isFlaggedFraud)
{
}

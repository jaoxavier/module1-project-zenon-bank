package br.com.zenon;

import br.com.zenon.fraud.entity.Transaction;
import br.com.zenon.fraud.entity.TransactionCustomer;
import br.com.zenon.fraud.enums.TransactionType;
import br.com.zenon.util.TransactionIngestor;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() throws FileNotFoundException {

        TransactionIngestor.ingest(new File("zenon-fraud-detector/data/PS_20174392719_1491204439457_log.csv"));

        Transaction transactionOne = new Transaction(1,
                TransactionType.PAYMENT,
                new BigDecimal("9839.64"),
                new TransactionCustomer(
                        "C1231006815",
                        new BigDecimal("170136.0"),
                        new BigDecimal("160296.36")
                ),
                new TransactionCustomer(
                        "M1979787155",
                        new BigDecimal("0.0"),
                        new BigDecimal("0.0")
                ),
                false, false
        );

        Transaction transaction = new Transaction(
                743,
                TransactionType.CASH_OUT,
                new BigDecimal("850002.52"),
                new TransactionCustomer(
                        "C1280323807",
                        new BigDecimal("850002.52"),
                        new BigDecimal("0.0")
                ),
                new TransactionCustomer(
                        "C873221189",
                        new BigDecimal("6510099.11"),
                        new BigDecimal("7360101.63")
                ),
                true, false
        );
    }
}

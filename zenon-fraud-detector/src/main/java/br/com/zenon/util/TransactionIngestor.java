package br.com.zenon.util;

import br.com.zenon.fraud.entity.Transaction;
import br.com.zenon.fraud.entity.TransactionCustomer;
import br.com.zenon.fraud.enums.TransactionType;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionIngestor {

    public static final String DELIMIT = ",";

    public static List<Transaction> ingest(File csv) throws FileNotFoundException {
        List<Transaction> transactionList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(DELIMIT);
                transactionList.add(parseTransaction(values));
            }
            
            return transactionList;
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Transaction parseTransaction(String[] values) {
        int step = Integer.parseInt(values[0]);
        TransactionType type = TransactionType.valueOf(values[1]);
        BigDecimal amount = new BigDecimal(values[2]);
        TransactionCustomer origin = new TransactionCustomer(values[3], new BigDecimal(values[4]), new BigDecimal(values[5]));
        TransactionCustomer recipient = new TransactionCustomer(values[6], new BigDecimal(values[7]), new BigDecimal(values[8]));
        boolean isFraud = Objects.equals(values[9], "1");
        boolean isFlaggedFraud = Objects.equals(values[10], "1");

        Transaction transaction = new Transaction(
                step, type, amount, origin, recipient, isFraud, isFlaggedFraud
        );
        return transaction;
    }
}

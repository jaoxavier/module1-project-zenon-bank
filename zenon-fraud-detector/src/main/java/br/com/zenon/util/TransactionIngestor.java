package br.com.zenon.util;

import br.com.zenon.fraud.entity.Transaction;
import br.com.zenon.fraud.entity.TransactionCustomer;
import br.com.zenon.fraud.enums.TransactionType;

import javax.swing.text.html.Option;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionIngestor {

    public static final String DELIMIT = ",";

    public TransactionIngestor() {
    }

    public Optional<List<Transaction>> ingest(File csv) throws FileNotFoundException {
        List<Transaction> transactionList = new ArrayList<>();
        String line = "";
        
        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(DELIMIT);
                transactionList.add(parseTransaction(values));
            }

            return Optional.of(transactionList);
        } catch (Exception e) {
            System.err.println("Error: " + line + " " + e);
            return Optional.empty();
        }
    }

    // THATS NOT MY OWN AWNSER, COURSE SUGGESTION
    public List<Transaction> read(String filename){
        Path path = Path.of(filename);

        try {
            List<String> lines = Files.readAllLines(path);
            return lines.stream()
                    .skip(1)
                    //.limit(1000)
                    .map(this::parseTransaction)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Transaction parseTransaction(String[] values) {
        try{
            int step = Integer.parseInt(values[0]);
            TransactionType type = TransactionType.valueOf(values[1]);
            BigDecimal amount = new BigDecimal(values[2]);
            TransactionCustomer origin = new TransactionCustomer(values[3], new BigDecimal(values[4]), new BigDecimal(values[5]));
            TransactionCustomer recipient = new TransactionCustomer(values[6], new BigDecimal(values[7]), new BigDecimal(values[8]));
            boolean isFraud = "1".equals(values[9]);
            boolean isFlaggedFraud = "1".equals(values[10]);

            return new Transaction(
                    step, type, amount, origin, recipient, isFraud, isFlaggedFraud
            );
        }
        catch (Exception e){
            System.err.println("Error " + e);
        }
        return null;
    }

    // THATS NOT MY OWN AWNSER, COURSE SUGGESTION
    private Transaction parseTransaction(String line) {
        String[] chunks = line.split(",");

        int step = Integer.parseInt(chunks[0]);
        TransactionType type = TransactionType.valueOf(chunks[1]);
        BigDecimal amount = new BigDecimal(chunks[2]);
        var origin = new TransactionCustomer(chunks[3], new BigDecimal(chunks[4]), new BigDecimal(chunks[5]));
        var recipient = new TransactionCustomer(chunks[6], new BigDecimal(chunks[7]), new BigDecimal(chunks[8]));
        boolean isFraud = "1".equals(chunks[9]);
        boolean isFlaggedFraud = "1".equals(chunks[10]);

        return new Transaction(step, type, amount, origin, recipient, isFraud, isFlaggedFraud);
    }
}

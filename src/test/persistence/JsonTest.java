package persistence;

import model.Transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

//based on JsonTest from JsonSerializationDemo
public class JsonTest {

    protected void checkTransaction (String id, int amount, String type) {
        Transaction transaction = new Transaction(id, amount, type);
        assertEquals(id, transaction.getTransactionId());
        assertEquals(amount, transaction.getTransactionAmount());
        assertEquals(type, transaction.getTransactionType());
    }
}

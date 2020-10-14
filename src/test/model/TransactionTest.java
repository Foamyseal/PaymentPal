package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {

    private Transaction transaction1;
    private Transaction transaction2;

    @BeforeEach
    void runBefore() {
        transaction1 = new Transaction(100, "Deposit");
        transaction2 = new Transaction(100, "Withdrawal");
    }

    @Test
    void testTransactionConstructor(){
        assertEquals(1, transaction1.getTransactionAmount());
        assertEquals("A234BCD", transaction1.getTransactionId());
        assertEquals("Deposit", transaction1.getTransactionType());
        assertEquals(100, transaction2.getTransactionAmount());
        assertEquals("A234BCD", transaction2.getTransactionId());
        assertEquals("Withdrawal", transaction2.getTransactionType());
    }

    @Test
    void testReturnTransaction(){
        //stub
    }

    @Test
    void testReturnMultipleTransactions(){
        //stub

    }

}


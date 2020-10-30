package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    private Transaction transaction1;
    private Transaction transaction2;

    @BeforeEach
    void runBefore() {
        transaction1 = new Transaction("1", 100, "Deposit");
        transaction2 = new Transaction("2", 200, "Withdrawal");
    }

    @Test
    void testSetTransactionAmount() {
        transaction1.setTransactionAmount(100);
        assertEquals(100, transaction1.getTransactionAmount());
        transaction2.setTransactionAmount(0);
        assertEquals(0, transaction2.getTransactionAmount());
    }

    @Test
    void testSetTransactionId() {
        transaction1.setTransactionId("D1");
        assertEquals("D1", transaction1.getTransactionId());
        transaction2.setTransactionId("W1");
        assertEquals("W1", transaction2.getTransactionId());
    }

    @Test
    void testSetTransactionType() {
        transaction1.setTransactionType("Deposit");
        assertEquals("Deposit", transaction1.getTransactionType());
        transaction2.setTransactionType("Withdrawal");
        assertEquals("Withdrawal", transaction2.getTransactionType());

    }

    @Test
    void testTransactionToString(){
        transaction1.setTransactionId("D1");
        transaction1.setTransactionType("Deposit");
        transaction1.setTransactionAmount(100);
        assertTrue(transaction1.toString().contains("|Transaction Id: D1 Amount: 100 Type: Deposit|"));
    }

}


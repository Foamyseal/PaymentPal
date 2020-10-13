package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {

    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;

    @BeforeEach
    void runBefore() {
        transaction1 = new Transaction("Martin", 1);
        transaction2 = new Transaction();
        transaction3 = new Transaction();
    }

    @Test
    void testTransactionConstructor(){
        assertEquals("Martin", transaction1.getTransactionName());
        assertEquals(1, transaction1.getTransactionAmount());
        assertEquals(0, transaction1.getTransactionId());
        assertEquals("Deposit", transaction1.getTransactionType());
    }

    @Test
    void testReturnTransaction(){
        assertEquals(transaction1, transaction1);
        assertEquals(new Transaction("Martin", 1), transaction1);

    }

    @Test
    void testReturnMultipleTransactions(){

    }

    @Test
    void testSingleWithdrawal(){

    }
0
    @Test
    void testConsecutiveWithdrawals(){

    }

    @Test
    void testDepositWithdrawal(){

    }

}


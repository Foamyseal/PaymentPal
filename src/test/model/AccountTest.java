package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    private Account account1;
    private Account account2;
    private Account account3;


    @BeforeEach
    void runBefore() {
        account1 = new Account("Martin", 1);
        account2 = new Account("Martin", 1000);
        account3 = new Account("Charlie", 1001);
    }

    @Test
    void testAccountConstructor(){
        assertEquals("Martin", account1.getUserName());
        assertEquals(1, account1.getBalance());


        assertEquals("Martin", account1.getUserName());
        assertEquals(1000, account1.getBalance());

        assertEquals("Charlie", account3.getUserName());
        assertEquals(1001, account3.getBalance());
    }

    @Test
    void testSingleDeposit(){
        account1.deposit(4);
        assertEquals(5, account1.getBalance());
    }

    @Test
    void testSingleWithdrawal(){
        account1.withdraw(10);
        assertEquals(25, account1.getBalance());
    }

    @Test
    void testConsecutiveDeposits(){
        account1.deposit(4);
        assertEquals(5, account1.getBalance());
        account1.deposit(10);
        assertEquals(15, account1.getBalance());
        account1.deposit(20);
        assertEquals(35, account1.getBalance());
    }

    @Test
    void testConsecutiveWithdrawals() {
        account1.withdraw(10);
        assertEquals(25, account1.getBalance());
        account1.withdraw(5);
        assertEquals(25, account1.getBalance());
        account1.withdraw(1);
        assertEquals(24, account1.getBalance());
    }
}


package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {
    private Account account1;
    private Account account2;
    private Account account3;
    private Account account4;


    @BeforeEach
    void runBefore() {
        account1 = new Account("Martin", 1);
        account2 = new Account("Martin", 1000);
        account3 = new Account("Charlie", 1001);
        account4 = new Account("Martin", 100);
    }

    @Test
    void testAccountConstructor(){
        assertEquals("Martin", account1.getUserName());
        assertEquals(1, account1.getBalance());
        assertEquals("A1BM1412", account1.getAccountID());
    }

    @Test
    void testSingleDeposit(){
        account1.deposit(4);
        assertEquals(5, account1.getBalance());
    }

    @Test
    void testSingleWithdrawal(){
        account2.withdraw(10);
        assertEquals(990, account2.getBalance());
    }

    @Test
    void testWithdrawalFailure(){
        account4.withdraw(100000);
        assertEquals(100, account4.getBalance());
    }

    @Test
    void testConsecutiveDeposits(){
        account3.deposit(10);
        assertEquals(1011, account3.getBalance());
        account3.deposit(9);
        assertEquals(1020, account3.getBalance());
        account3.deposit(20);
        assertEquals(1040, account3.getBalance());
    }

    @Test
    void testConsecutiveWithdrawals() {
        account4.withdraw(10);
        assertEquals(90, account4.getBalance());
        account4.withdraw(5);
        assertEquals(85, account4.getBalance());
        account4.withdraw(1);
        assertEquals(84, account4.getBalance());
    }

    @Test
    void testAddSingleTransaction() {
        Transaction transaction = new Transaction("", 0, "");
        account2.addTransaction(transaction);
        ArrayList<Transaction> testSingleTransaction = new ArrayList<>();
        testSingleTransaction.add(transaction);
        assertEquals(testSingleTransaction, account2.getTransactionHistory());

    }

    @Test
    void testAddMultipleTransactions() {
        Transaction transaction1 = new Transaction("", 0, "");
        Transaction transaction2 = new Transaction("", 0, "");
        Transaction transaction3 = new Transaction("", 0, "");
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction3);
        ArrayList<Transaction> testTransaction = new ArrayList<>();
        testTransaction.add(transaction1);
        testTransaction.add(transaction2);
        testTransaction.add(transaction3);
        assertEquals(testTransaction, account1.getTransactionHistory());
    }

    @Test
    void testGetDepositOnly() {
        Transaction transaction1 = new Transaction("", 0, "Deposit");
        Transaction transaction2 = new Transaction("", 0, "Deposit");
        Transaction transaction3 = new Transaction("", 0, "Deposit");
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction3);
        ArrayList<Transaction> testTransaction = new ArrayList<>();
        testTransaction.add(transaction1);
        testTransaction.add(transaction2);
        testTransaction.add(transaction3);
        assertEquals(testTransaction, account1.getDepositHistory());
    }

    @Test
    void testGetDepositOnlyFail() {
        Transaction transaction1 = new Transaction("", 0, "Withdrawal");
        Transaction transaction2 = new Transaction("", 0, "Withdrawal");
        Transaction transaction3 = new Transaction("", 0, "Withdrawal");
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction3);
        ArrayList<Transaction> testTransaction = new ArrayList<>();
        assertEquals(testTransaction, account1.getDepositHistory());
    }

    @Test
    void testGetWithdrawalsOnly() {
        Transaction transaction1 = new Transaction("", 0, "Withdrawal");
        Transaction transaction2 = new Transaction("", 0, "Withdrawal");
        Transaction transaction3 = new Transaction("", 0, "Withdrawal");
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction3);
        ArrayList<Transaction> testTransaction = new ArrayList<>();
        testTransaction.add(transaction1);
        testTransaction.add(transaction2);
        testTransaction.add(transaction3);
        assertEquals(testTransaction, account1.getWithdrawalHistory());
    }

    @Test
    void testGetWithdrawalsOnlyFail() {
        Transaction transaction1 = new Transaction("", 0, "Deposit");
        Transaction transaction2 = new Transaction("", 0, "Deposit");
        Transaction transaction3 = new Transaction("", 0, "Deposit");
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction3);
        ArrayList<Transaction> testTransaction = new ArrayList<>();
        assertEquals(testTransaction, account1.getWithdrawalHistory());
    }

    @Test
    void testGetDepositOnlyMix() {
        Transaction transaction1 = new Transaction("", 0, "Deposit");
        Transaction transaction2 = new Transaction("", 0, "Withdrawal");
        Transaction transaction3 = new Transaction("", 0, "Deposit");
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction3);
        ArrayList<Transaction> testTransaction = new ArrayList<>();
        testTransaction.add(transaction1);
        testTransaction.add(transaction3);
        assertEquals(testTransaction, account1.getDepositHistory());
    }

    @Test
    void testGetWithdrawalOnlyMix() {
        Transaction transaction1 = new Transaction("", 0, "Deposit");
        Transaction transaction2 = new Transaction("", 0, "Withdrawal");
        Transaction transaction3 = new Transaction("", 0, "Deposit");
        account1.addTransaction(transaction1);
        account1.addTransaction(transaction2);
        account1.addTransaction(transaction3);
        ArrayList<Transaction> testTransaction = new ArrayList<>();
        testTransaction.add(transaction2);
        assertEquals(testTransaction, account1.getWithdrawalHistory());
    }
}


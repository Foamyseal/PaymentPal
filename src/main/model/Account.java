package model;


import java.lang.reflect.Array;
import java.util.ArrayList;

// an account with name of user, current balance, account ID and transactions
public class Account {
    private String accountID;
    private String userName;
    private int balance;
    private ArrayList<Transaction> transactions = new ArrayList<>();


    public Account(String name, int initialBalance) {
        this.userName = name;
        this.balance = initialBalance;
        // enable user to change ID in phase 2
        this.accountID = "A1BM1412";
    }

    public String getAccountID() {
        return accountID;
    }

    public String getUserName() {
        return userName;
    }

    public int getBalance() {
        return balance;
    }

    //REQUIRES: amount > 0
    //MODIFIES: this
    //EFFECTS: add amount to balance
    public int deposit(int amount) {
        balance = getBalance() + amount;
        return balance;
    }

    //REQUIRES: amount > 0
    //MODIFIES: this
    //EFFECTS: subtract amount to balance if there is sufficient balance in account
    public int withdraw(int amount) {
        if (getBalance() > amount) {
            balance = getBalance() - amount;
        }
        return balance;
    }

    //MODIFIES: this
    //EFFECTS: add transaction to transactions
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    //EFFECTS: return transactions
    public ArrayList<Transaction> getTransactionHistory() {
        return transactions;
    }
}
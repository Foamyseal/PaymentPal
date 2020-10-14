package model;


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

    public int deposit(int amount) {
        balance = getBalance() + amount;
        return balance;
    }

    public int withdraw(int amount) {
        balance = getBalance() - amount;
        return balance;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactions;
    }
}
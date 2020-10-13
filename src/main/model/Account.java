package model;


// an account with name of user, current balance, and account ID.
public class Account {
    private int accountID;
    private String userName;
    private int balance;

    private Transaction transaction;

    public Account(String userName, int initialBalance) {
        name = userName;
        balance = intialBalance;
    }

    public int getAccountID() {
        return 0;
    }

    public String getUserName() {
        return "";
    }

    public int getBalance() {
        return 0;
    }

    public int deposit (int amount) {
        return 0;
    }

    public int withdraw (int amount) {
        return 0;
    }

}
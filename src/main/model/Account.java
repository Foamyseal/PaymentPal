package model;


// an account with name of user, current balance, and account ID.
public class Account {
    private String accountID;
    private String userName;
    private int balance;

    private Transaction transaction;

    public Account(String name, int initialBalance) {
        this.userName = userName;
        this.balance = initialBalance;
        this.accountID = "A1BM1412";
    }

    public String getAccountID() {
        return accountID;
    }

    public String getUserName() {
        return "";
    }

    public int getBalance() {
        return 0;
    }

    public int deposit(int amount) {
        //set withdrawal type to be "Deposit"
        return 0;
    }

    public int withdraw(int amount) {
        //set withdrawal type to be "Withdrawal"
        return 0;
    }

    private void logTransactions(){

    }

}
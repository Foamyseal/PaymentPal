package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.lang.reflect.Array;
import java.util.ArrayList;

// an account with name of user, current balance, account ID and transactions
public class Account implements Writeable {
    private String accountID;
    private String userName;
    private int balance;
    private ArrayList<Transaction> transactions = new ArrayList<>();

    //initialize Account
    public Account(String name, int initialBalance) {
        this.userName = name;
        this.balance = initialBalance;
        // enable user to change ID in phase 3
        this.accountID = "A1BM1412";
    }

    //implement in UI in phase 2 to return account details

    //EFFECTS: get account ID
    public String getAccountID() {
        return accountID;
    }

    //EFFECTS: get account user name
    public String getUserName() {
        return userName;
    }

    //EFFECTS get account balance
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


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", userName);
        json.put("id", accountID);
        json.put("balance", balance);
        json.put("transactions", transactionsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray transactionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transaction t : transactions) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }
}
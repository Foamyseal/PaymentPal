package model;

import java.util.ArrayList;
import java.util.List;

public class Transaction {

    private String transactionId;
    private int transactionAmount;
    private String transactionType;


    public Transaction(int amount, String type) {
        this.transactionId = "A234BCD";
        this.transactionAmount = amount;
        this.transactionType = type;
    }


    public int getTransactionId() {
        return 0;
    }

    public String getTransactionType() {
        return "";
    }


    public int getTransactionAmount() {
        return 0;
    }
}

package model;

import org.json.JSONObject;
import persistence.Writeable;

// a transaction that contains transaction ID, amount, and type of transaction
public class Transaction implements Writeable  {

    private String transactionId;
    private int transactionAmount;
    private String transactionType;


    // MODIFIES: this
    // EFFECTS: creates new transaction
    public Transaction(String id, int amount, String type) {
        transactionId = id;
        transactionAmount = amount;
        transactionType = type;
    }

    // MODIFIES: this
    //EFFECTS: set transaction ID
    public void setTransactionId(String id) {
        this.transactionId = id;
    }

    // MODIFIES: this
    // EFFECTS: set transaction amount
    public void setTransactionAmount(int amount) {
        this.transactionAmount = amount;
    }

    // MODIFIES: this
    // EFFECTS: set transaction type
    public void setTransactionType(String type) {
        this.transactionType = type;
    }

    // EFFECTS: return transaction Id
    public String getTransactionId() {
        return transactionId;
    }

    // EFFECTS: return transaction amount
    public int getTransactionAmount() {
        return transactionAmount;
    }

    //EFFECTS: return transaction type
    public String getTransactionType() {
        return transactionType;
    }

    //EFFECTS: turns transaction to string
    @Override
    public String toString() {
        return "Transaction Id: " + this.getTransactionId() + " Amount: $" + this.getTransactionAmount() + " Type: "
                + this.getTransactionType();
    }

    //from JsonSerializationDemo
    //EFFECTS: return Transaction as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", transactionId);
        json.put("type", transactionType);
        json.put("amount", transactionAmount);
        return json;
    }
}

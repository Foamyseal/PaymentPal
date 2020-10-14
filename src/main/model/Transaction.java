package model;


public class Transaction {

    private String transactionId;
    private int transactionAmount;
    private String transactionType;


    // MODIFIES: this
    // EFFECTS: creates new transaction
    public Transaction() {
        transactionId = "";
        transactionAmount = 0;
        transactionType = "unknown";
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

    // EFFECTS: return transactionId
    public String getTransactionId() {
        return transactionId;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }
}

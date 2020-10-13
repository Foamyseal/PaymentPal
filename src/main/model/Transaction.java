package model;

public class Transaction {

    private int transactionID;
    private String name;
    private int amount;
    private String transactionType;


    public Transaction (String transactionName, int transactionAmount, String transactionType) {

        this.name = transactionName;
        amount = transactionAmount;
        type = transactionType;

    }


    public int deposit(int amount) {
//        balance = balance + amount;
        return 0;
    }

    public int withdraw (int amount) {
//        balance = getBalance() - amount;
        return 0;
    }

    public void transactionHistory() {
        //stub
    }


}

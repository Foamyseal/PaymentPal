package ui;

import model.Account;
import model.Transaction;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// UI based on TellerApp
public class Bank {
    private static final String JSON_STORE = "./data/account.json";
    private Scanner input;
    private Account account;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // Run Bank
    public Bank() throws FileNotFoundException {
        runBank();
    }

    //Constructor comment:
    private void runBank() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nPeace out :)");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("d")) {
            doDeposit();
        } else if (command.equals("w")) {
            doWithdrawal();
        } else if (command.equals("t")) {
            printTransactionHistory(account);
        } else if (command.equals("c")) {
            printBalance(account);
        } else if (command.equals("s")) {
            saveAccount();
        } else if (command.equals("l")) {
            loadAccount();
        } else {
            System.out.println("Invalid Selection");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        account = new Account("Martin", 0);
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> check current balance");
        System.out.println("\td -> deposit credits");
        System.out.println("\tw -> withdraw credits");
        System.out.println("\tt -> transaction history");
        System.out.println("\ts -> save account to file");
        System.out.println("\tl -> load account from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: conducts a deposit transaction
    private void doDeposit() {
        System.out.print("Enter amount to deposit: $");
        int amount = input.nextInt();

        if (amount >= 0) {
            Transaction t = new Transaction("", 0, "");
            account.deposit(amount);
            account.addTransaction(t);
            t.setTransactionAmount(amount);
            t.setTransactionType("Deposit");
            t.setTransactionId("D112934");
            System.out.println("Success!");
        } else {
            System.out.println("Deposit Amount Invalid, please enter a positive value\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts a withdraw transaction
    private void doWithdrawal() {
        System.out.print("Enter amount to withdraw: $");
        int amount = input.nextInt();

        if (amount < 0) {
            System.out.println("Please enter a positive withdrawal amount\n");
        } else if (account.getBalance() < amount) {
            System.out.println("Insufficient balance on account to perform withdrawal\n");
            printBalance(account);
        } else {
            Transaction t = new Transaction("", 0, "");
            account.withdraw(amount);
            account.addTransaction(t);
            t.setTransactionAmount(amount);
            t.setTransactionType("Withdraw");
            t.setTransactionId("TW123");
            System.out.println("Success!");
        }
    }

    // EFFECTS: prints balance of account to the screen
    private void printBalance(Account selected) {
        System.out.println(account.getBalance());
    }


    //EFFECTS: prints transaction history to the screen.
    private void printTransactionHistory(Account selected) {
        System.out.println(account.getTransactionHistory());
    }


    // EFFECTS: saves the account to file
    private void saveAccount() {
        try {
            jsonWriter.open();
            jsonWriter.write(account);
            jsonWriter.close();
            System.out.println("Saved " + account.getUserName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads account from file
    private void loadAccount() {
        try {
            account = jsonReader.read();
            System.out.println("Loaded " + account.getUserName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}

package ui;

import model.Account;
import model.Transaction;

import java.util.Scanner;

// UI based on TellerApp
public class Bank {
    private Scanner input;
    private Account acc1;


    public Bank() {
        runBank();
    }

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
            printTransactionHistory(acc1);
        } else if (command.equals("c")) {
            printBalance(acc1);
        } else {
            System.out.println("Invalid Selection");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        acc1 = new Account("Martin", 0);
        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tc -> check current balance");
        System.out.println("\td -> deposit credits");
        System.out.println("\tw -> withdraw credits");
        System.out.println("\tt -> transaction history");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: conducts a deposit transaction
    private void doDeposit() {
        System.out.print("Enter amount to deposit: $");
        int amount = input.nextInt();

        if (amount >= 0) {
            Transaction t = new Transaction();
            acc1.deposit(amount);
            acc1.addTransaction(t);
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
        } else if (acc1.getBalance() < amount) {
            System.out.println("Insufficient balance on account to perform withdrawal\n");
            printBalance(acc1);
        } else {
            Transaction t = new Transaction();
            acc1.withdraw(amount);
            acc1.addTransaction(t);
            t.setTransactionAmount(amount);
            t.setTransactionType("Withdraw");
            t.setTransactionId("TW123");
            System.out.println("Success!");
        }
    }

    // EFFECTS: prints balance of account to the screen
    private void printBalance(Account selected) {
        System.out.println(acc1.getBalance());
    }


    //EFFECTS: prints transaction history to the screen.
    private void printTransactionHistory(Account selected) {
        System.out.println(acc1.getTransactionHistory());
    }
}

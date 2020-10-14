package ui;

import model.Account;
import model.Transaction;

import java.util.Scanner;

public class Bank {
    private Scanner input;
    private Account acc1;
    private Transaction t1;


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
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("d")) {
            doDeposit();
        } else if (command.equals("w")) {
            doWithdrawal();
        } else if (command.equals("t")) {
//            doTransactionHistory();
        } else if (command.equals("c")) {
            printBalance(acc1);
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        acc1 = new Account("Martin", 145);
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
            acc1.deposit(amount);
            t1.setTransactionAmount(amount);
            t1.setTransactionType("Deposit");
            t1.setTransactionId("D1");
//            acc1.addTransaction();
        } else {
            System.out.println("Cannot deposit negative amount...\n");
        }

        printBalance(acc1);
    }

    // MODIFIES: this
    // EFFECTS: conducts a withdraw transaction
    private void doWithdrawal() {
        System.out.print("Enter amount to withdraw: $");
        int amount = input.nextInt();

        if (amount < 0) {
            System.out.println("Cannot withdraw negative amount...\n");
        } else if (acc1.getBalance() < amount) {
            System.out.println("Insufficient balance on account...\n");
        } else {
            acc1.withdraw(amount);
            t1.setTransactionType("Withdraw");
            t1.setTransactionId("T1");
//            acc1.addTransaction();
        }

        printBalance(acc1);
    }

    // EFFECTS: prints balance of account to the screen
    private void printBalance(Account selected) {
        System.out.printf("Balance: $%.2f\n", acc1.getBalance());
    }

    private void printTransactionHistory(Account selected) {
        System.out.printf("Transaction", acc1.getTransactionHistory());
    }
}

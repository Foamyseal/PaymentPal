package ui;

import model.exceptions.NoBalanceException;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new Bank();
        } catch (FileNotFoundException | NoBalanceException e) {
            System.out.println("Unable to run Bank: save file not found");
        }

    }
}

package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new Bank();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run Bank: save file not found");
        }

    }
}

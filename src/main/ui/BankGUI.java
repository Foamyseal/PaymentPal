package ui;

import model.Account;
import model.Transaction;
import model.exceptions.NoBalanceException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

import static java.lang.Integer.parseInt;

public class BankGUI extends JPanel  {

    private Account account;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private final JTextField amount;
    private final DefaultListModel<Transaction> listModel;

    private static final String depositString = "Deposit";
    private static final String withdrawString = "Withdraw";
    private static final String JSON_STORE = "./data/account.json";

    public BankGUI() {
        super(new BorderLayout());
        account = new Account("Martin", 0);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        listModel = new DefaultListModel<>();
        amount = new JTextField(6);

        transactionListGUI();
        runButtonPane();
    }

    //EFFECTS: create list of transactions and place in scroll pane.
    private void transactionListGUI() {
        JList<Transaction> transactionList = new JList<>(listModel);
        transactionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        transactionList.setSelectedIndex(0);
        transactionList.setPreferredSize(new Dimension(300, 50));
        transactionList.setVisibleRowCount(50);
        JScrollPane listScrollPane = new JScrollPane(transactionList);
        listScrollPane.setName("Previous Transaction List");
        listScrollPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        listScrollPane.setSize(300,50);
        add(listScrollPane, BorderLayout.CENTER);
    }


    //EFFECTS: creates button panel
    private void runButtonPane() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.PAGE_AXIS));
        JLabel jlabel1 = new JLabel("Welcome to PaymentPal!");
        JLabel jlabel2 = new JLabel("Created by Martin Au-yeung");
        buttonPane.add(jlabel1);
        buttonPane.add(jlabel2);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(loadAccount());
        buttonPane.add(saveAccount());
        buttonPane.add(createTransaction());
        buttonPane.add(showBalance());
        buttonPane.add(showDepositsOnly());
        buttonPane.add(showWithdrawalsOnly());
        buttonPane.add(showAllTransactions());
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        add(buttonPane, BorderLayout.EAST);
    }

    //EFFECTS: displays transactionFrame UI
    private Component createTransaction() {
        JButton createTransaction = new JButton("Create new transaction");
        createTransaction.setActionCommand("Create new transaction");
        createTransaction.addActionListener(e -> transactionFrame());
        return createTransaction;
    }

    //EFFECTS: construct transactionFrame UI
    private void transactionFrame() {
        JFrame transactionFrame = new JFrame("New Transaction");
        transactionFrame.getContentPane().add(transactionPane());
        transactionFrame.setSize(500, 125);
        transactionFrame.setLocationRelativeTo(null);
        transactionFrame.setVisible(true);
        transactionFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    //EFFECTS: display deposit & withdrawal buttons and amount field in transactionFrame
    private Component transactionPane() {
        JPanel transactionPane = new JPanel();
        JLabel jlabel = new JLabel("Enter amount of credits to deposit or withdraw from your account:");
        transactionPane.add(jlabel);
        transactionPane.add(Box.createHorizontalStrut(5));
        transactionPane.add(new JSeparator(SwingConstants.VERTICAL));
        transactionPane.add(Box.createHorizontalStrut(5));
        transactionPane.add(amount);
        transactionPane.add(depositButton());
        transactionPane.add(withdrawalButton());
        return transactionPane;
    }

    // MODIFIES: this
    // EFFECTS: click to deposit credits from account
    private Component depositButton() {
        JButton depositButton = new JButton(depositString);
        depositButton.setActionCommand(depositString);
        depositButton.addActionListener(e -> {
            try {
                int parsedAmount = parseInt(amount.getText());
                if (parsedAmount >= 0) {
                    doDeposit();
                } else {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,
                            "error, please enter positive integer");
                }
            } catch (NumberFormatException exception) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "ERROR: Please enter an integer value!");
            }
        });
        return depositButton;
    }

    //EFFECTS: click to withdraw credits from account
    private Component withdrawalButton() {
        JButton withdrawButton = new JButton(withdrawString);
        withdrawButton.setActionCommand(withdrawString);
        withdrawButton.addActionListener(e -> {
            try  {
                if (parseInt(amount.getText()) < 0) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,
                            "error, please enter a positive integer");
                } else {
                    doWithdrawal();
                }
            } catch (NumberFormatException exception) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "ERROR: Please enter an integer!");
            } catch (NoBalanceException noBalanceException) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Not enough Balance in account!");
            }
        });
        return withdrawButton;
    }

    // MODIFIES: this
    // EFFECTS: conducts a withdraw transaction
    private void doWithdrawal() throws NoBalanceException {
        int parsedAmount = parseInt(amount.getText());

        Transaction t = new Transaction("", 0, "");
        account.withdraw(parsedAmount);
        account.addTransaction(t);
        t.setTransactionAmount(parsedAmount);
        t.setTransactionType("Withdrawal");
        t.setTransactionId("T" + Math.round(Math.random() * (9999 - 1000 + 1) + 1));
        listModel.addElement(t);
        amount.requestFocusInWindow();
        amount.setText("");
        playSound("bling.wav");
    }

    // MODIFIES: this
    //EFFECTS: conducts a deposit transaction
    private void doDeposit() {
        int parsedAmount = parseInt(amount.getText());
        Transaction t = new Transaction("", 0, "");
        account.deposit(parsedAmount);
        account.addTransaction(t);
        t.setTransactionAmount(parsedAmount);
        t.setTransactionType("Deposit");
        t.setTransactionId("D" + Math.round(Math.random() * (9999 - 1000 + 1) + 1));
        listModel.addElement(t);
        amount.requestFocusInWindow();
        amount.setText("");
        playSound("bling.wav");
    }

    // EFFECTS: saves the account to file
    private Component saveAccount() {
        JButton saveButton = new JButton("Save");
        saveButton.setActionCommand("Save");
        saveButton.addActionListener(e -> {
            try {
                jsonWriter.open();
                jsonWriter.write(account);
                jsonWriter.close();
                JOptionPane.showMessageDialog(null, "Saved "
                        + account.getUserName() + " to " + JSON_STORE);
            } catch (FileNotFoundException exception) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE);
            }
        });
        return saveButton;
    }


    // MODIFIES: this
    // EFFECTS: loads account from file
    private Component loadAccount() {
        JButton loadButton = new JButton("Load account");
        loadButton.setActionCommand("Load account");
        loadButton.addActionListener(e -> {
            try {
                account = jsonReader.read();
                for (int i = 0; i < account.getTransactionHistory().size(); i++) {
                    listModel.addElement(account.getTransactionHistory().get(i));
                }
                JOptionPane.showMessageDialog(null, "Loaded "
                        + account.getUserName() + " from " + JSON_STORE);
            } catch (IOException exception) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE);
            }
        });
        return loadButton;
    }

    // EFFECTS: shows balance of account to the screen
    private Component showBalance() {
        JButton showBalance = new JButton("Check account balance");
        showBalance.setActionCommand("Check account balance");
        showBalance.addActionListener(e -> {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Account Balance = $" + account.getBalance());
        });
        return showBalance;
    }

    // MODIFIES: this
    //EFFECTS: show deposit type transactions only
    private Component showDepositsOnly() {
        JButton showDepositsOnly = new JButton("Show deposits only");
        showDepositsOnly.setActionCommand("Show deposits only");
        showDepositsOnly.addActionListener(e -> {
            listModel.removeAllElements();
            for (int i = 0; i < account.getDepositHistory().size(); i++) {
                listModel.addElement(account.getDepositHistory().get(i));
            }
        });
        return showDepositsOnly;
    }

    // MODIFIES: this
    //EFFECTS: show withdrawal type transactions only
    private Component showWithdrawalsOnly() {
        JButton showWithdrawalsOnly = new JButton("Show withdrawals only");
        showWithdrawalsOnly.setActionCommand("Show Withdrawal only");
        showWithdrawalsOnly.addActionListener(e -> {
            listModel.removeAllElements();
            for (int i = 0; i < account.getWithdrawalHistory().size(); i++) {
                listModel.addElement(account.getWithdrawalHistory().get(i));
            }
        });
        return showWithdrawalsOnly;
    }

    //MODIFIES: this
    //EFFECTS: show All transactions
    private Component showAllTransactions() {
        JButton showAllTransactions = new JButton("Show all transactions");
        showAllTransactions.setActionCommand("Show all transactions");
        showAllTransactions.addActionListener(e -> {
            listModel.removeAllElements();
            for (int i = 0; i < account.getTransactionHistory().size(); i++) {
                listModel.addElement(account.getTransactionHistory().get(i));
            }
        });
        return showAllTransactions;
    }

    //EFFECTS: plays chaching sound
    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception exception) {
            System.out.println("sound error");
            exception.printStackTrace();
        }
    }


    private static void createAndShowGUI() {
        JFrame frame = new JFrame("PaymentPal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new BankGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(BankGUI::createAndShowGUI);
    }


}

package ui;

import javafx.scene.control.Alert;
import model.Account;
import model.Transaction;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class BankGUI extends JPanel  {

    private JList<String> transactionList;
    private Account account;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JTextField amount;
    private DefaultListModel<Transaction> listModel;

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
        transactionList = new JList(listModel);
        transactionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        transactionList.setSelectedIndex(0);
        transactionList.setSize(600, 400);
        transactionList.setVisibleRowCount(10);
        JScrollPane listScrollPane = new JScrollPane(transactionList);
        listScrollPane.setName("Previous Transaction List");
        listScrollPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(listScrollPane, BorderLayout.WEST);
    }


    private void runButtonPane() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.PAGE_AXIS));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(loadAccount());
        buttonPane.add(saveAccount());
        buttonPane.add(createTransaction());
        buttonPane.add(showBalance());
        buttonPane.add(showDepositsOnly());
        buttonPane.add(showWithdrawalsOnly());
        buttonPane.add(showAllTransactions());
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        add(buttonPane, BorderLayout.CENTER);
    }

    private Component createTransaction() {
        JButton createTransaction = new JButton("create transaction");
        createTransaction.setActionCommand("create transaction");
        createTransaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transactionFrame();
            }
        });
        return createTransaction;
    }

    private void transactionFrame() {

        JFrame transactionFrame = new JFrame("New Transaction");

        transactionFrame.getContentPane().add(transactionPane());
        transactionFrame.setSize(500, 300);
        transactionFrame.setLocationRelativeTo(null);
        transactionFrame.setVisible(true);

    }

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
    // EFFECTS: conducts a deposit transaction
    private Component depositButton() {
        JButton depositButton = new JButton(depositString);
        depositButton.setActionCommand(depositString);
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        return depositButton;
    }

    //Withdrawal Component
    private Component withdrawalButton() {
        JButton withdrawButton = new JButton(withdrawString);
        withdrawButton.setActionCommand(withdrawString);
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try  {
                    if (parseInt(amount.getText()) < 0) {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null,
                                "error, please enter a positive integer");
                    } else if (account.getBalance() < parseInt(amount.getText())) {
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null, "Not enough Balance in account!");
                    } else {
                        doWithdrawal();
                    }
                } catch (NumberFormatException exception) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "ERROR: Please enter an integer!");
                }
            }
        });
        return withdrawButton;
    }

    // MODIFIES: this
    // EFFECTS: conducts a withdraw transaction
    private void doWithdrawal() {
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
    }

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
    }


    // EFFECTS: saves the account to file
    private Component saveAccount() {
        JButton saveButton = new JButton("save");
        saveButton.setActionCommand("save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JButton saveButton = new JButton("save");
                    saveButton.setActionCommand("save");
                    jsonWriter.open();
                    jsonWriter.write(account);
                    jsonWriter.close();
                    JOptionPane.showMessageDialog(null, "Saved "
                            + account.getUserName() + " to " + JSON_STORE);
                } catch (FileNotFoundException exception) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE);
                }
            }
        });
        return saveButton;
    }


    // MODIFIES: this
    // EFFECTS: loads account from file
    private Component loadAccount() {
        JButton loadButton = new JButton("load");
        loadButton.setActionCommand("load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
        return loadButton;
    }

    // EFFECTS: prints balance of account to the screen
    private Component showBalance() {
        JButton showBalance = new JButton("Check Balance");
        showBalance.setActionCommand("Check Balance");
        showBalance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Account Balance = $" + account.getBalance());
            }
        });
        return showBalance;
    }

    private Component showDepositsOnly() {
        JButton showDepositsOnly = new JButton("Show deposits only");
        showDepositsOnly.setActionCommand("Show deposits only");
        showDepositsOnly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.removeAllElements();
                for (int i = 0; i < account.getDepositHistory().size(); i++) {
                    listModel.addElement(account.getDepositHistory().get(i));
                }
            }
        });
        return showDepositsOnly;
    }


    private Component showWithdrawalsOnly() {
        JButton showWithdrawalsOnly = new JButton("Show withdrawals only");
        showWithdrawalsOnly.setActionCommand("Show Withdrawal only");
        showWithdrawalsOnly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.removeAllElements();
                for (int i = 0; i < account.getWithdrawalHistory().size(); i++) {
                    listModel.addElement(account.getWithdrawalHistory().get(i));
                }
            }
        });
        return showWithdrawalsOnly;
    }

    private Component showAllTransactions() {
        JButton showAllTransactions = new JButton("Show all transactions");
        showAllTransactions.setActionCommand("Show all transactions");
        showAllTransactions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.removeAllElements();
                for (int i = 0; i < account.getTransactionHistory().size(); i++) {
                    listModel.addElement(account.getTransactionHistory().get(i));
                }
            }
        });
        return showAllTransactions;
    }


    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("PaymentPal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new BankGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }


}

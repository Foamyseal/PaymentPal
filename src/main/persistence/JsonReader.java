package persistence;

import model.Account;
import model.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads account from file and returns it
    // throws IOException if error occurs when reading data

    public Account read() throws IOException {
        String jsonData  = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccount(jsonObject);
    }

    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Account parseAccount(JSONObject jsonObject) {
        String accountName = jsonObject.getString("name");
        int accountBalance = jsonObject.getInt("balance");
        Account a = new Account(accountName, accountBalance);
        addTransactions(a, jsonObject);
        return a;
    }

    // MODIFIES: a
    // EFFECTS: parses transactions from JSON object and adds them to workroom
    private void addTransactions(Account a, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("transactions");
        for (Object json : jsonArray) {
            JSONObject nextTransaction = (JSONObject) json;
            addTransaction(a, nextTransaction);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addTransaction(Account a, JSONObject jsonObject) {
        String id = jsonObject.getString("id");
        int amount = jsonObject.getInt("amount");
        String type = jsonObject.getString("type");
        Transaction transaction = new Transaction(id, amount, type);
        a.addTransaction(transaction);
    }


}

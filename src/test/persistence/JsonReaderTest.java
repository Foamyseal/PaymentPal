package persistence;

import model.Account;
import model.Transaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Account a = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAccount.json");
        try {
            Account a = reader.read();
            assertEquals("Martin", a.getUserName());
            assertEquals("A1BM1412", a.getAccountID());
            assertEquals(0, a.getTransactionHistory().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAccount.json");
        try {
            Account a = reader.read();
            assertEquals("Martin", a.getUserName());
            assertEquals("A1BM1412", a.getAccountID());
            List<Transaction> transactions = a.getTransactionHistory();
            assertEquals(2, transactions.size());
            checkTransaction("1", 100, "Deposit");
            checkTransaction("2", 50, "Withdrawal");

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

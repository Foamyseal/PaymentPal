package persistence;

import model.Account;
import model.Transaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Account a = new Account("", 0);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccount() {
        try {
            Account a = new Account("Martin", 0);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAccount.json");
            writer.open();
            writer.write(a);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAccount.json");
            a = reader.read();
            assertEquals("Martin", a.getUserName());
            assertEquals("A1BM1412", a.getAccountID());
            assertEquals(0, a.getTransactionHistory().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterAccount() {
        try {
            Account a = new Account("Martin", 0);
            a.addTransaction(new Transaction("1", 100, "Deposit"));
            a.addTransaction(new Transaction("2", 50, "Withdrawal"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAccount.json");
            writer.open();
            writer.write(a);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAccount.json");
            a = reader.read();
            assertEquals("Martin", a.getUserName());
            assertEquals("A1BM1412", a.getAccountID());
            List<Transaction> transactions = a.getTransactionHistory();
            assertEquals(2, transactions.size());
            checkTransaction("1", 100, "Deposit");
            checkTransaction("2", 50, "Withdrawal");

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

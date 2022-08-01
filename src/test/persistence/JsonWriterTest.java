package persistence;


import model.Passwords;
import model.WifiPassword;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: Parts of this code were adapted from JsonSerializerDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/

public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Passwords wr = new Passwords();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPasswords() {
        try {
            Passwords p = new Passwords();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPasswords.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPasswords.json");
            p = reader.read();
            assertEquals(0, p.getPasswords().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPasswords() {
        try {
            List<String> devices = new ArrayList<String>();
            List<String> people = new ArrayList<String>();

            devices.add("iPhone 12");
            people.add("Bob");


            Passwords p = new Passwords();
            p.addPassword(new WifiPassword("test", "1234", "12345", devices, people));
            p.addPassword(new WifiPassword("test2", "4321", "password", devices, people));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPasswords.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPasswords.json");
            p = reader.read();
            assertEquals("test", p.getPasswordByName("test").getName());
            List<WifiPassword> passwords = p.getPasswords();
            assertEquals(2, passwords.size());
//            checkThingy("saw", Category.METALWORK, thingies.get(0));
//            checkThingy("needle", Category.STITCHING, thingies.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}

package persistence;

import model.Passwords;
import model.WifiPassword;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


// CITATION: Parts of this code were adapted from JsonSerializerDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/

public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Passwords p = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkPasswords() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPasswords.json");
        try {
            Passwords p = reader.read();
            assertEquals(0, p.getPasswords().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPasswords() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPasswords.json");
        try {
            Passwords p = reader.read();
            List<WifiPassword> passwords = p.getPasswords();
            assertEquals(2, passwords.size());
//            checkThingy("needle", Category.STITCHING, thingies.get(0));
//            checkThingy("saw", Category.WOODWORK, thingies.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

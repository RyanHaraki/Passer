package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {
    Passwords testPasswords;
    WifiPassword password;
    List<String> devices;
    List<String> people;

    @BeforeEach
    public void setup() {
        testPasswords = new Passwords();

        devices = new ArrayList<String>();
        people = new ArrayList<String>();
        devices.add("iPhone X");
        people.add("Billy");

        password = new WifiPassword("test", "1234 street", "password", devices, people);
    }

    @Test
    public void testAddPassword() {
        assertEquals(0, this.testPasswords.getPasswords().size());
        this.testPasswords.addPassword(this.password);
        // Did the size of the passwords array increase by 1?
        assertEquals(1, this.testPasswords.getPasswords().size());
        // Is the name of the password at end of array == name of password added?
        assertEquals("test", this.testPasswords.getPasswords().get(0).getName());
    }

    @Test
    public void testRemovePasswordByNameSuccess() {
        this.testPasswords.addPassword(this.password);
        assertEquals(1, this.testPasswords.getPasswords().size());

        // Did the size of the passwords array decrease by 1?
        this.testPasswords.removePasswordByName("test");
        assertEquals(0, this.testPasswords.getPasswords().size());
    }

    @Test
    public void testRemovePasswordByNameFail() {
        this.testPasswords.addPassword(this.password);
        assertEquals(1, this.testPasswords.getPasswords().size());

        // Is the size of the passwords array unchanged?
        this.testPasswords.removePasswordByName("1234");
        assertEquals(1, this.testPasswords.getPasswords().size());
    }

    @Test
    public void testGetPasswordByNameSuccess() {
        this.testPasswords.addPassword(this.password);
        assertEquals(this.password, this.testPasswords.getPasswordByName("test"));
    }

    @Test
    public void testGetPasswordByNameFail(){
        this.testPasswords.addPassword(this.password);
        assertNull(this.testPasswords.getPasswordByName("1234"));
    }

}

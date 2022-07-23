package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Tests for WifiPassword class
class WifiPasswordTest {
    WifiPassword testPassword;
    List<String> devices;
    List<String> people;

    @BeforeEach
    public void setup() {
        devices = new ArrayList<String>();
        people = new ArrayList<String>();
        devices.add("iPhone X");
        people.add("Billy");

        testPassword = new WifiPassword("test", "1234 street", "password", devices, people);
    }

    @Test
    public void testConstructor() {
        assertEquals("test", this.testPassword.getName());
        assertEquals("1234 street", this.testPassword.getAddress());
        assertEquals("password", this.testPassword.getPassword());
        assertEquals(this.devices, this.testPassword.getDevices());
        assertEquals(this.people, this.testPassword.getPeople());
    }

    @Test
    public void testChangeName() {
        testPassword.changeName("bob");
        assertEquals("bob", testPassword.getName());
    }

    @Test
    public void testChangeAddress() {
        testPassword.changeAddress("4321");
        assertEquals("4321", testPassword.getAddress());
    }

    @Test
    public void testChangePassword() {
        testPassword.changePassword("pass");
        assertEquals("pass", testPassword.getPassword());
    }

    @Test
    public void testAddDevice() {
        assertEquals(1, testPassword.getDevices().size());
        testPassword.addDevice("Macbook Pro");
        //  Is size of devices array 1 item larger?
        assertEquals(2, testPassword.getDevices().size());
        // Is the item at the newly added index equals to the item added?
        assertEquals("Macbook Pro", testPassword.getDevices().get(1));
    }

    @Test
    public void testRemoveDevice() {
        assertEquals(1, testPassword.getDevices().size());
        testPassword.removeDevice("iPhone X");
        // Is size of devices array 1 item smaller?
        assertEquals(0, testPassword.getDevices().size());
    }

    @Test
    public void testAddPerson() {
        assertEquals(1, testPassword.getPeople().size());
        testPassword.addPerson("Joe");
        //  Is size of people array 1 item larger?
        assertEquals(2, testPassword.getPeople().size());
        // Is the item at the newly added index equals to the item added?
        assertEquals("Joe", testPassword.getPeople().get(1));
    }

    @Test
    public void testRemovePerson() {
        assertEquals(1, testPassword.getPeople().size());
        testPassword.removePerson("Billy");
        // Is size of devices array 1 item smaller?
        assertEquals(0, testPassword.getPeople().size());
    }

}

package persistence;

import model.WifiPassword;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// CITATION: Parts of this code were adapted from JsonSerializerDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/

public class JsonTest {
    protected void checkWifiPasswords(String name, String password, String address,
                                      List<String> devices, List<String> people,
                                      WifiPassword wifiPassword) {
        assertEquals(name, wifiPassword.getName());
        assertEquals(password, wifiPassword.getPassword());
        assertEquals(address, wifiPassword.getAddress());
        assertEquals(devices, wifiPassword.getDevices());
        assertEquals(people, wifiPassword.getPeople());
    }
}
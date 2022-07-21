package model;

import java.util.ArrayList;
import java.util.List;

public class Passwords {
    private List<WifiPassword> passwords = new ArrayList<WifiPassword>();

    // MODIFIES: this
    // EFFECTS: adds password to list of passwords
    public void addPassword(WifiPassword password) {
        passwords.add(password);
    }

    // MODIFIES: this
    // EFFECTS: remove password from passwords based on the password name
    public void removePasswordByName(String name) {
        this.passwords.removeIf(password -> name.equals(password.getName()));
    }

    // EFFECTS: returns list of wifi passwords
    public List<WifiPassword> getPasswords() {
        return this.passwords;
    }

    // EFFECTS: returns specific wifi password and its data based on password name
    public WifiPassword getPasswordByName(String name) {
        for (WifiPassword password: this.passwords) {
            if (name.equals(password.getName())) {
                return password;

            }
        }
        return null; // CHANGE THIS (maybe this works if if doesnt)
    }
}

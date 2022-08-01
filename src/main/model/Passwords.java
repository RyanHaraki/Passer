package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// CITATION: Parts of this code were adapted from JsonSerializerDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/

// Class representing a list of passwords
public class Passwords implements Writable {
    private List<WifiPassword> passwords = new ArrayList<WifiPassword>();

    // REQUIRES: password name does not already exist in Passwords
    // MODIFIES: this
    // EFFECTS: adds password to list of passwords
    public void addPassword(WifiPassword password) {
        passwords.add(password);
    }

    // REQUIRES: name length is > 0
    // MODIFIES: this
    // EFFECTS: remove password from passwords based on the password name
    public void removePasswordByName(String name) {
        this.passwords.removeIf(password -> name.equals(password.getName()));
    }

    // EFFECTS: returns an unmodifiable list of wifi passwords
    public List<WifiPassword> getPasswords() {
        return Collections.unmodifiableList(this.passwords);
    }

    // EFFECTS: returns specific wifi password and its data based on password name
    public WifiPassword getPasswordByName(String name) {
        for (WifiPassword password: this.passwords) {
            if (name.equals(password.getName())) {
                return password;
            }
        }
        return null;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("passwords", passwordsToJson());
        return json;
    }

    // EFFECTS: returns WifiPasswords in this Passwords as json array
    private JSONArray passwordsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (WifiPassword p: passwords) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}

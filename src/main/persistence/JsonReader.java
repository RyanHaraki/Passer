package persistence;

// CITATION: Parts of this code were adapted from JsonSerializerDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/

import model.Passwords;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import model.WifiPassword;
import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs json reader to read from given source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads passwords from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Passwords read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePasswords(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses passwords from JSON object and returns it
    private Passwords parsePasswords(JSONObject jsonObject) {
        Passwords p = new Passwords();
        addWifiPasswords(p, jsonObject);
        return p;
    }

    // MODIFIES: wr
    // EFFECTS: parses wifipasswords from JSON object and adds them to passwords
    private void addWifiPasswords(Passwords p, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("passwords");
        for (Object json : jsonArray) {
            JSONObject nextWifiPassword = (JSONObject) json;
            addWifiPassword(p, nextWifiPassword);
        }
    }

    // MODIFIES: p
    // EFFECTS: parses wifipasswords metadata from JSON object and adds it to password
    private void addWifiPassword(Passwords p, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String password = jsonObject.getString("password");
        String address = jsonObject.getString("address");

        JSONArray devices = jsonObject.getJSONArray("devices");
        List<String> actualDevices = new ArrayList<String>();

        JSONArray people = jsonObject.getJSONArray("people");
        List<String> actualPeople = new ArrayList<String>();

        for (Object log: devices) {
            String device = (String) log;
            actualDevices.add(device);
        }

        for (Object log: people) {
            String person = (String) log;
            actualPeople.add(person);
        }

        WifiPassword passwordToAdd = new WifiPassword(name, address, password, actualDevices, actualPeople);
        p.addPassword(passwordToAdd);
    }
}




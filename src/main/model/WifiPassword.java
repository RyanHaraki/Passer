package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.List;

// CITATION: Parts of this code were adapted from JsonSerializerDemo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/

// Class representing a WiFi password and its associated metadata
public class WifiPassword implements Writable, Observer {
    private String name;
    private String address;
    private String password;
    private List<String> devices;
    private List<String> people;

    public WifiPassword(String name, String address, String password, List<String> devices, List<String> people) {
        this.name = name;
        this.address = address;
        this.password = password;
        this.devices = devices;
        this.people = people;
    }


    // MODIFIES: this
    // EFFECTS: edits wifi name
    public void changeName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: edits wifi address
    public void changeAddress(String address) {
        this.address = address;
    }

    // MODIFIES: this
    // EFFECTS: edits wifi name
    public void changePassword(String password) {
        this.password = password;
    }

    // MODIFIES: this
    // EFFECTS: adds device to list of devices
    public void addDevice(String device) {
        this.devices.add(device);
    }

    // MODIFIES: this
    // EFFECTS: removes specific device from devices
    public void removeDevice(String device) {
        this.devices.remove(device);
    }

    // MODIFIES: this
    // EFFECTS: adds person to list of people
    public void addPerson(String person) {
        this.people.add(person);
    }

    // MODIFIES: this
    // EFFECTS: removes specific person from people
    public void removePerson(String person) {
        this.people.remove(person);
    }

    // GETTERS

    // EFFECTS: returns name associated with password
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns address associated with password
    public String getAddress() {
        return this.address;
    }

    // EFFECTS: returns wifi password
    public String getPassword() {
        return this.password;
    }

    // EFFECTS: returns devices associated with password
    public List<String> getDevices() {
        return this.devices;
    }


    // EFFECTS: returns people associated with password
    public List<String> getPeople() {
        return this.people;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("address", this.address);
        json.put("devices", this.devices);
        json.put("password", this.password);
        json.put("people", this.people);
        return json;
    }

    @Override
    public void update(WifiPassword p) {
        System.out.println("Password " + p.getName() + " added.");
    }
}

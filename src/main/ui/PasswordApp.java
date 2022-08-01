package ui;

import model.Passwords;
import model.WifiPassword;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class representing the terminal application for a password tracker
public class PasswordApp {
    private static final String JSON_STORE = "./data/passwords.json";
    private Passwords passwords;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the teller application
    public PasswordApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runPassword();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runPassword() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                savePasswords();
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nThanks for using Wifi Password Tracker!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "a":
                addPassword();
                break;
            case "g":
                getPassword();
                break;
            case "e":
                editPassword();
                break;
            case "d":
                deletePassword();
                break;
            default:
                System.out.println("Selection invalid...");
                break;
        }
    }


    // MODIFIES: this
    // EFFECTS: initializes accounts and loads passwords from JSON
    private void init() {
        passwords = new Passwords();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        loadPasswords();
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add password");
        System.out.println("\tg -> get password by name");
        System.out.println("\te -> edit password");
        System.out.println("\td -> delete password");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: adds a new password to the list of passwords
    private void addPassword() {
        // Prompt user for new password info
        System.out.println("Enter a new Password: ");
        String newPass = input.next();
        System.out.println("Enter a name for this Password: ");
        String newName = input.next();
        System.out.println("What is the address of this passwords home? ");
        String newAddress = input.next();

        // create an empty list of people and devices
        List<String> newDevices = new ArrayList<String>();
        List<String> newPeople = new ArrayList<String>();

        WifiPassword newWifiPassword = new WifiPassword(newName, newAddress, newPass, newDevices, newPeople);

        this.passwords.addPassword(newWifiPassword);
    }

    // MODIFIES: this
    // EFFECTS: deletes a password from list of passwords
    private void deletePassword() {
        // Prompt user for password name
        System.out.println("Enter the name of the password you'd like to delete: ");
        String name = input.next();

        WifiPassword foundPassword = this.passwords.getPasswordByName(name);

        if (foundPassword != null) {
            this.passwords.removePasswordByName(name);
            System.out.println("Password successfully deleted");
        } else {
            System.out.println("Password not found.");
        }
    }

    // MODIFIES: this
    // EFFECTS: edits a password
    private void editPassword() {
        // Prompt user for password name
        System.out.println("Enter the name of the password you'd like to edit: ");
        String name = input.next();
        String choice = null;

        WifiPassword passwordToEdit = this.passwords.getPasswordByName(name);
        if (passwordToEdit != null) {
            // prompt user for what they want to edit
            displayEditMenu(passwordToEdit, name);
            choice = input.next();
            // edit prompt (switch case)
            handleEditPassword(choice, passwordToEdit);
            System.out.println("-------------------------");
            System.out.println("Password successfully edited!");

        } else {
            System.out.println("Password not found, please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Helper function to edit password
    private void handleEditPassword(String choice, WifiPassword passwordToEdit) {
        switch (choice) {
            case "n":
                updateName(passwordToEdit);
                break;
            case "a":
                updateAddress(passwordToEdit);
                break;
            case "p": // pass
                updatePassword(passwordToEdit);
                break;
            case "d": // devices
                updateDevices(passwordToEdit);
                break;
            case "pe": // people
                updatePeople(passwordToEdit);
                break;
            default:
                System.out.println("Selection invalid...");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: helper function to update name
    private void updateName(WifiPassword passwordToEdit) {
        System.out.println("Enter new name: ");
        String name = input.next();
        passwordToEdit.changeName(name);
    }

    // MODIFIES: this
    // EFFECTS: helper function to update address
    private void updateAddress(WifiPassword passwordToEdit) {
        System.out.println("Enter new address: ");
        String address = input.next();
        passwordToEdit.changeAddress(address);
    }

    // MODIFIES: this
    // EFFECTS: helper function to update password
    private void updatePassword(WifiPassword passwordToEdit) {
        System.out.println("Enter new password: ");
        String password = input.next();
        passwordToEdit.changePassword(password);
    }

    // MODIFIES: this
    // EFFECTS: helper function to update devices
    private void updateDevices(WifiPassword passwordToEdit) {
        System.out.println("Would you like to add or remove a device from this network?");
        System.out.println("-------------------------");
        System.out.println("a - Add");
        System.out.println("r - Remove");
        String decision = input.next();
        String deviceName;

        if (decision.equals("a")) {
            System.out.println("Name of new device: ");
            deviceName = input.next();
            passwordToEdit.addDevice(deviceName);
            System.out.println("Device successfully added!");
        } else if (decision.equals("r")) {
            System.out.println("Name of device to remove: ");
            deviceName = input.next();
            passwordToEdit.removeDevice(deviceName);
            System.out.println("Device successfully removed!");
        } else {
            System.out.println("Invalid selection");
        }
    }

    // MODIFIES: this
    // EFFECTS: helper function to update people
    private void updatePeople(WifiPassword passwordToEdit) {
        System.out.println("Would you like to add or remove a person from this network?");
        System.out.println("-------------------------");
        System.out.println("a - Add");
        System.out.println("r - Remove");
        String decision = input.next();
        String personName;

        if (decision.equals("a")) {
            System.out.println("Name of new person: ");
            personName = input.next();
            passwordToEdit.addPerson(personName);
            System.out.println("Person successfully added!");
        } else if (decision.equals("r")) {
            System.out.println("Name of person to remove: ");
            personName = input.next();
            passwordToEdit.removePerson(personName);
            System.out.println("Person successfully removed!");
        } else {
            System.out.println("Invalid selection");
        }

    }


    // EFFECTS: Displays menu of options when user is editing a password
    private void displayEditMenu(WifiPassword passwordToEdit, String name) {
        System.out.println("What field of " + name + " would you like to edit?");
        System.out.println("-------------------------");
        System.out.println("n - name (current value: " + passwordToEdit.getName() + ")");
        System.out.println("a - Address (current value: " + passwordToEdit.getAddress() + ")");
        System.out.println("p - Password (current value: " + passwordToEdit.getPassword() + ")");
        System.out.println("d - Devices on network (current value: " + passwordToEdit.getDevices() + ")");
        System.out.println("pe - People using network (current value: " + passwordToEdit.getPeople() + ")");
    }


    // MODIFIES: this
    // EFFECTS: gets a password from the list of passwords
    private void getPassword() {
        System.out.println("Enter a password name: ");
        String name = input.next();
        WifiPassword retrievedPassword = passwords.getPasswordByName(name);

        if (retrievedPassword != null) {
            System.out.println("Getting information for password " + retrievedPassword.getName());
            System.out.println("-------------------------");
            System.out.println("Address: " + retrievedPassword.getAddress());
            System.out.println("Password: " + retrievedPassword.getPassword());
            System.out.println("Devices on network: " + retrievedPassword.getDevices());
            System.out.println("People using network: " + retrievedPassword.getPeople());
        } else {
            System.out.println("Password not found.");
        }
    }

    // EFFECTS: saves passwords to a file
    private void savePasswords() {
        try {
            jsonWriter.open();
            jsonWriter.write(passwords);
            jsonWriter.close();
            System.out.println("Saved passwords to: " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads passwords from file
    private void loadPasswords() {
        try {
            passwords = jsonReader.read();
            System.out.println("Loaded passwords from: " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

    }
}

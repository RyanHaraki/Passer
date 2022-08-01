package ui;

import java.io.FileNotFoundException;

// Class representing the environment in which the PassWordApp program is run
public class Main {
    public static void main(String[] args) {
        try {
            new PasswordApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}

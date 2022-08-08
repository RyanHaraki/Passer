package ui;

import model.Passwords;
import model.WifiPassword;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.event.*;

// Graphical User Interface for password app
public class PasswordGui extends JPanel implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;

    private Passwords loadedPasswords;
    private Passwords passwordsList;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final String ADD_STRING = "Add";
    private static final String REMOVE_STRING = "Remove";
    private static final String JSON_STORAGE = "./data/passwords.json";
    private static final String SAVE_STRING = "Save";
    private static final String LOAD_STRING = "Load";
    private static final String DISPLAY_STRING = "Display Panel";
    private static final String IMAGE_ROUTE = "./data/meme.gif";

    private JButton removeButton;
    private JButton addButton;
    private JButton loadButton;
    private JLabel passwordInfo;
    private JTextField passwordName;
    private JTextField passwordPass;
    private JTextField passwordAddress;
    private JTextField passwordPeople;
    private JTextField passwordDevices;
    private JLabel passwordsSaved;
    private JButton saveButton;
    private ImageIcon image;

    private JButton displayPanelButton;
    private JFrame displayPanel;

    JPanel infoPane;
    JPanel wrapperPane;
    JPanel buttonPane;

    private AddListener addListener;

    // Constructor
    // EFFECTS: constructs GUI
    public PasswordGui() {
        super(new BorderLayout());

        initializeFields();

        JScrollPane listScrollPane = new JScrollPane(list);
        addListener = new AddListener(addButton);

        //Create the list and put it in a scroll pane.
        initListModel();

        MouseListener mouseListener = initMouseListener();
        list.addMouseListener(mouseListener);

        createButtons();
        initButtons();

        // Create a panel for the password info section
        createFields();

        // Create Panes
        infoPane = createInfoPane();
        wrapperPane = createWrapperPane(infoPane);
        buttonPane = createButtonPane();
        createFieldPane(buttonPane);

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
        add(wrapperPane, BorderLayout.EAST);
    }

    // MODIFIES: this
    //EFECTS: initializes fields and gives them values
    private void initializeFields() {
        removeButton = new JButton(REMOVE_STRING);
        addButton = new JButton(ADD_STRING);
        saveButton = new JButton(SAVE_STRING);
        loadButton = new JButton(LOAD_STRING);
        image = new ImageIcon(IMAGE_ROUTE);
        loadedPasswords = new Passwords();
        passwordsList = new Passwords();
        jsonWriter = new JsonWriter(this.JSON_STORAGE);
        jsonReader = new JsonReader(this.JSON_STORAGE);
        listModel = new DefaultListModel();
        displayPanelButton = new JButton(DISPLAY_STRING);
        displayPanel = new JFrame("Pass Info - Display");

        list = new JList(listModel);
    }

    // MODIFIES: this
    // EFFECTS: initializes input fields
    private void createFields() {
        passwordName = initField(passwordName);
        passwordPass = initField(passwordPass);
        passwordAddress = initField(passwordAddress);
        passwordPeople = initField(passwordPeople);
        passwordDevices = initField(passwordDevices);
    }

    // MODIFIES: this
    // EFFECTS: initializes a field
    private JTextField initField(JTextField field) {
        field = new JTextField(10);
        field.addActionListener(addListener);
        field.getDocument().addDocumentListener(addListener);
        return field;
    }

    // MODIFIES: this
    // EFFECTS: Creates the info display panel
    private JPanel createInfoPane() {
        List<String> emptyDevices = new ArrayList<>();
        List<String> emptyPeople = new ArrayList<>();
        JPanel infopane = new JPanel();
        passwordInfo = new JLabel(displayPassword(new WifiPassword("", "",
                "", emptyDevices, emptyPeople)));
        infopane.add(passwordInfo);
        return infopane;
    }

    // MODIFIES: this
    // EFFECTS: Initializes buttons
    private void initButtons() {
        initializeButton(addButton, ADD_STRING, new AddListener(addButton));
        initializeButton(removeButton, REMOVE_STRING, new RemoveListener());
        initializeButton(saveButton, SAVE_STRING, new SaveListener());
        initializeButton(loadButton, LOAD_STRING, new LoadListener());
        initializeButton(displayPanelButton, DISPLAY_STRING, new DisplayListener());

    }

    // MODIFIES: this
    // EFFECTS: creates list model
    private void initListModel() {
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
    }

    // MODIFIES: this
    // EFFECTS: Creates wrapper pane for password info
    private JPanel createWrapperPane(JPanel infopane) {
        JPanel wrapperPane = new JPanel();
        wrapperPane.setLayout(new BoxLayout(wrapperPane, BoxLayout.Y_AXIS));
        wrapperPane.add(infopane);
        wrapperPane.add(saveButton);
        wrapperPane.add(loadButton);
        wrapperPane.add(displayPanelButton);
        passwordsSaved = new JLabel("");
        wrapperPane.add(passwordsSaved);
        return wrapperPane;
    }

    // MODIFIES: this
    // EFFECTS: listener for mouse clicks in list
    private MouseListener initMouseListener() {
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String selectedItem = (String) list.getSelectedValue();
                WifiPassword gotPass = passwordsList.getPasswordByName(selectedItem);
                System.out.println(gotPass.getName());
                passwordInfo.setText(displayPassword(gotPass));
            }
        };
        return mouseListener;
    }


    // MODIFIES: this
    // EFFECTS: creates a button pane for the user to add and remove passwords
    private JPanel createButtonPane() {
        //Create a panel that uses BoxLayout. -> THIS IS WHERE THE BUTTONS ARE
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.Y_AXIS));
        buttonPane.add(removeButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,0,5,5));
        return buttonPane;
    }

    // MODIFIES: this
    // EFFECTS: creates field pane
    private JPanel createFieldPane(JPanel buttonPane) {
        JPanel fieldPane = new JPanel();
        fieldPane.setLayout(new BoxLayout(fieldPane, BoxLayout.Y_AXIS));
        fieldPane.add(new JLabel("<html><h1>Add a new Password</h1></html>"));
        fieldPane.add(new JLabel("Name"));
        fieldPane.add(passwordName);
        fieldPane.add(new JLabel("Password"));
        fieldPane.add(passwordPass);
        fieldPane.add(new JLabel("Address"));
        fieldPane.add(passwordAddress);
        fieldPane.add(new JLabel("People (Comma seperated)"));
        fieldPane.add(passwordPeople);
        fieldPane.add(new JLabel("Devices (Comma seperated)"));
        fieldPane.add(passwordDevices);
        fieldPane.add(new JSeparator(SwingConstants.HORIZONTAL));
        fieldPane.add(addButton);
        buttonPane.add(fieldPane);
        return buttonPane;
    }

    // MODIFIES: this
    // EFFECTS: creates buttons
    private void createButtons() {
        removeButton = new JButton(REMOVE_STRING);
        addButton = new JButton(ADD_STRING);
        loadButton = new JButton(LOAD_STRING);
        saveButton = new JButton(SAVE_STRING);
    }

    // MODIFIES: this
    // EFFECTS:  initializes a button
    private void initializeButton(JButton button, String label, ActionListener listener) {
        button.setActionCommand(label);
        button.addActionListener(listener);

        if (Objects.equals(label, "Remove")) {
            button.setEnabled(false);
        }
    }

    // MODIFIES: this
    // EFFECTS: listener for save button clicks
    class SaveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            savePasswords();
            passwordsSaved.setText("Passwords saved to Json!");
            JLabel imageLabel = new JLabel(image);
            imageLabel.setIcon(image);
            wrapperPane.add(imageLabel);
            System.out.println("Passwords saved to Json!");
        }
    }

    // MODIFIES: this
    // EFFECTS: listener for load button clicks
    class LoadListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            loadPasswords();

            // load passwords from json
            for (WifiPassword pass: loadedPasswords.getPasswords()) {
                listModel.addElement(pass.getName());
            }

            if (loadedPasswords.getPasswords().size() < 1) {
                passwordsSaved.setText("JSON empty - no passwords loaded.");
                System.out.println("JSON empty - no passwords loaded.");
            } else {
                passwordsSaved.setText("Passwords loaded from Json!");
                System.out.println("Passwords loaded from Json!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: listener for remove button clicks
    class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            // delete from passwords list
            String name = listModel.get(index).toString();

            passwordsList.removePasswordByName(name);
            listModel.remove(index);
            System.out.println("Removed: " + name);

            int size = listModel.getSize();

            if (size == 0) { //no passwords left, disable removing.
                removeButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);

            }
        }
    }

    // MODIFIES: this
    // EFFECTS: listener for display button clicks
    class DisplayListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            displayPanel.setPreferredSize(new Dimension(200, 200));
            displayPanel.setAlwaysOnTop(true);

            displayPanel.add(passwordInfo);

            displayPanel.pack();
            displayPanel.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: listener for add button clicks
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        // MODIFIES: this
        // EFFECTS: adds password to list
        public void actionPerformed(ActionEvent e) {
            String name = passwordName.getText();
            String pass = passwordPass.getText();
            String add = passwordAddress.getText();
            List<String> people = Arrays.asList(passwordPeople.getText().split(","));
            List<String> devices = Arrays.asList(passwordDevices.getText().split(","));

            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                passwordName.requestFocusInWindow();
                passwordName.selectAll();
                return;
            }

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            addNewPassword(name, pass, add, people, devices);

            //Reset the text field.
            passwordName.requestFocusInWindow();
            resetFields();

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        // EFFECTS: tests for string equality
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        // EFFECTS: handles insertion updates
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        // EFFECTS: handles remove updates
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        // EFFECTS: enable button if field not empty
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // EFFECTS: enables button
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // EFFECTS: returns true if text field empty
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds new password to passwords list
    private void addNewPassword(String name, String pass, String add, List<String> people, List<String> devices) {
        List<String> peopleList = new ArrayList<>();
        List<String> deviceList = new ArrayList<>();

        for (String person: people) {
            peopleList.add(person);
        }

        for (String device: devices) {
            deviceList.add(device);
        }

        listModel.addElement(passwordName.getText());

        // add new password to passwords
        passwordsList.addPassword(new WifiPassword(name, add,
                pass, deviceList, peopleList));
    }

    // MODIFIES: this
    // EFFECTS: resets fields to blanks
    private void resetFields() {
        passwordName.setText("");
        passwordPass.setText("");
        passwordPeople.setText("");
        passwordAddress.setText("");
        passwordDevices.setText("");
    }

    // EFFECTS: listens for value changed in field
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                removeButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                removeButton.setEnabled(true);
            }
        }
    }

    // EFFECTS: Returns password details formatted in string
    public String displayPassword(WifiPassword password) {
        String people = "";
        String devices = "";

        if (password.getPeople().size() < 1) {
            people = "None";
        } else {
            for (String person: password.getPeople()) {
                people = people + person + ", ";
            }
        }

        if (password.getDevices().size() < 1) {
            devices = "None";
        } else {
            for (String device: password.getDevices()) {
                devices = devices + device + ", ";
            }

        }

        return ("<html><strong>Name: </strong>" + password.getName() + "<br>" + "<strong>Address: </strong>"
                + password.getAddress() + "<br>" + "<strong>Password: </strong>"
                + password.getPassword() + "</strong><br>" + "<strong>Devices: </strong>" + devices + "<br>"
                + "<strong>People: </strong>" + people) + "</html>";
    }

    // MODIFIES: this
    // EFFECTS: loads passwords from file
    private void loadPasswords() {
        try {
            loadedPasswords = jsonReader.read();
            System.out.println("Loaded passwords from: " + JSON_STORAGE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORAGE);
        }
    }

    // EFFECTS: saves passwords to a file
    private void savePasswords() {
        try {
            jsonWriter.open();
            jsonWriter.write(passwordsList);
            jsonWriter.close();
            System.out.println("Saved passwords to: " + JSON_STORAGE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORAGE);
        }
    }

  // EFFECTS: creates and shows GUI
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Password App");
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new PasswordGui();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
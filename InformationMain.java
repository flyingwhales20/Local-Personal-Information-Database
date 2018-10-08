package infodatabase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class InformationMain extends JFrame {

    private JTextField txtFirst;
    private JTextField txtLast;
    private JTextField txtAge;
    private JTextField txtAddress;
    private JTextField txtCity;
    private JTextField txtState;
    private JTextField txtZip;
    private JTextField txtSSN1;
    private JTextField txtSSN2;
    private JTextField txtSSN3;
    private JButton btnFind;
    private JButton btnAdd;
    private JButton btnClose;
    private JButton btnReset;
    private JLabel lblFirst;
    private JLabel lblLast;
    private JLabel lblDOBmonth;
    private JLabel lblDOBday;
    private JLabel lblDOByear;
    private JLabel lblAge;
    private JLabel lblAddress;
    private JLabel lblCity;
    private JLabel lblState;
    private JLabel lblZip;
    private JLabel lblGender;
    private JLabel lblSSN;
    private JComboBox<String> cmboGender;
    private JComboBox<String> cmboDOBmonth;
    private JComboBox<String> cmboDOBday;
    private JComboBox<String> cmboDOByear;

    //Class variables that hold data
    private String firstName;
    private String lastName;
    private String DOBmonth;
    private String DOBday;
    private String DOByear;
    private String age;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String SSN;
    private String gender;
    ArrayList<String> personalData = new ArrayList<>();

    InformationMain() {
        super("Pseudo Database");
        JPanel f1 = new JPanel(null);
        generalGUI format = new generalGUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(340, 410);
        setLocation(400, 100);
        setResizable(false);

        //Add first name 
        lblFirst = new JLabel("First name:");

        txtFirst = new JTextField();
        txtFirst.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField text = (JTextField) e.getSource();
                text.setText(format.capitalizeProper(text.getText(), text.getText().length(), 1));

            }
        });

        //Add last name
        lblLast = new JLabel("Last name:");

        txtLast = new JTextField();
        txtLast.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField text = (JTextField) e.getSource();

                text.setText(format.capitalizeProper(text.getText(), text.getText().length(), 1));
            }
        });

        JPanel pFirst = new JPanel(new BorderLayout());
        pFirst.add(lblFirst, BorderLayout.NORTH);
        pFirst.add(txtFirst, BorderLayout.SOUTH);

        JPanel pLast = new JPanel(new BorderLayout());
        pLast.add(lblLast, BorderLayout.NORTH);
        pLast.add(txtLast, BorderLayout.SOUTH);

        JPanel pFirstLast = new JPanel(new GridLayout(0, 2, 10, 0));
        pFirstLast.add(pFirst);
        pFirstLast.add(pLast);
        pFirstLast.setBounds(10, 10, 210, 40);

        //Listener to calculate age automatically
        ActionListener calcAge = new ActionListener() {
            int curYear = Calendar.getInstance().get(Calendar.YEAR);
            int curMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
            int curDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            int tempAge = 0;

            public void actionPerformed(ActionEvent e) {
                if (cmboDOBmonth.getSelectedIndex() > 0 && cmboDOBday.getSelectedIndex() > 0
                        && cmboDOByear.getSelectedIndex() > 0) {
                    tempAge = Year.now().getValue() - Integer.parseInt((String) cmboDOByear.getSelectedItem());
                    if (curMonth < cmboDOBmonth.getSelectedIndex()) {
                        tempAge--;
                    }
                    else if (curMonth == cmboDOBmonth.getSelectedIndex()
                            && curDay < cmboDOBday.getSelectedIndex()) {
                        tempAge--;
                    }
                    txtAge.setText(String.valueOf(tempAge));
                }
            }
        };

        //Add DOB drop downs
        lblDOBmonth = new JLabel("Birth Month: ");

        String[] months = {"", "01 - Jan", "02 - Feb", "03 - Mar", "04 - Apr",
            "05 - May", "06 - June", "07 - July", "08 - Aug",
            "09 - Sep", "10 - Oct", "11 - Nov", "12 - Dec"};

        cmboDOBmonth = new JComboBox<>(months);
        cmboDOBmonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cmboDOBday.setSelectedIndex(0);
                generateDOBValues(cmboDOBmonth.getSelectedIndex(), false);
            }
        });

        lblDOBday = new JLabel("Day: ");
        cmboDOBday = new JComboBox<>();
        cmboDOBday.addItem("");
        cmboDOBday.addActionListener(calcAge);

        lblDOByear = new JLabel("Year: ");
        cmboDOByear = new JComboBox<>();
        cmboDOByear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cmboDOByear.getSelectedIndex() > 0) {
                    generateDOBValues(cmboDOBmonth.getSelectedIndex(), false);
                }
            }
        });
        cmboDOByear.addActionListener(calcAge);

        //Set default values for DOB fields
        generateDOBValues(0, true);

        JPanel pDOBmonth = new JPanel(new BorderLayout());
        pDOBmonth.add(lblDOBmonth, BorderLayout.NORTH);
        pDOBmonth.add(cmboDOBmonth, BorderLayout.SOUTH);
        pDOBmonth.setBounds(10, 60, 80, 40);

        JPanel pDOBday = new JPanel(new BorderLayout());
        pDOBday.add(lblDOBday, BorderLayout.NORTH);
        pDOBday.add(cmboDOBday, BorderLayout.SOUTH);
        pDOBday.setBounds(120, 60, 80, 40);

        JPanel pDOByear = new JPanel(new BorderLayout());
        pDOByear.add(lblDOByear, BorderLayout.NORTH);
        pDOByear.add(cmboDOByear, BorderLayout.SOUTH);
        pDOByear.setBounds(230, 60, 80, 40);

        //Add age
        lblAge = new JLabel("Age:");

        txtAge = new JTextField();
        txtAge.setEditable(false);

        JPanel pAge = new JPanel(new BorderLayout());
        pAge.add(lblAge, BorderLayout.NORTH);
        pAge.add(txtAge, BorderLayout.SOUTH);
        pAge.setBounds(230, 10, 40, 40);

        //Add address info
        lblAddress = new JLabel("Address:");

        txtAddress = new JTextField();
        txtAddress.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField text = (JTextField) e.getSource();

                text.setText(format.capitalizeProper(text.getText(), text.getText().length(), -1));
            }
        }
        );

        JPanel pAddress = new JPanel(new BorderLayout());
        pAddress.add(lblAddress, BorderLayout.NORTH);
        pAddress.add(txtAddress, BorderLayout.SOUTH);
        pAddress.setBounds(10, 110, 300, 40);

        lblCity = new JLabel("City:");

        txtCity = new JTextField();
        txtCity.setColumns(6);
        txtCity.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField text = (JTextField) e.getSource();
                text.setText(format.capitalizeProper(text.getText(), text.getText().length(), 1));
            }
        });

        JPanel pCity = new JPanel(new BorderLayout());
        pCity.add(lblCity, BorderLayout.WEST);
        pCity.add(txtCity, BorderLayout.EAST);
        pCity.setBounds(10, 170, 100, 20);

        lblState = new JLabel("State:");

        txtState = new JTextField();
        txtState.setName("State");
        txtState.setColumns(2);
        txtState.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField text = (JTextField) e.getSource();

                text.setText(text.getText().toUpperCase());
                text.setText(format.limitLength(text.getText(), 2));
                text.setText(format.capitalizeProper(text.getText(), text.getText().length(), 1));

                if (text.getText().length() == 2) {
                    txtZip.requestFocus();
                }
            }
        });

        JPanel pState = new JPanel(new BorderLayout());
        pState.add(lblState, BorderLayout.WEST);
        pState.add(txtState, BorderLayout.EAST);
        pState.setBounds(120, 170, 65, 20);

        lblZip = new JLabel("Zip:");

        txtZip = new JTextField();
        txtZip.setColumns(5);
        txtZip.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField text = (JTextField) e.getSource();

                text.setText(format.limitLength(text.getText(), 5));
                text.setText(format.capitalizeProper(text.getText(), text.getText().length(), 0));

                if (text.getText().length() == 5) {
                    cmboGender.requestFocus();
                }
            }
        });

        JPanel pZip = new JPanel(new BorderLayout());
        pZip.add(lblZip, BorderLayout.WEST);
        pZip.add(txtZip, BorderLayout.EAST);
        pZip.setBounds(195, 170, 85, 20);

        //Add gender
        lblGender = new JLabel("Gender: ");

        String[] listGender = {" ", "Male", "Female"};
        cmboGender = new JComboBox<>(listGender);
        cmboGender.setSelectedIndex(0);

        JPanel pGender = new JPanel();
        pGender.add(lblGender);
        pGender.add(cmboGender);
        pGender.setBounds(0, 210, 140, 40);

        //Add SSN
        lblSSN = new JLabel("SSN:  ");

        txtSSN1 = new JTextField();
        txtSSN1.setColumns(3);
        txtSSN1.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField text = (JTextField) e.getSource();

                text.setText(format.limitLength(text.getText(), 3));
                text.setText(format.capitalizeProper(text.getText(), text.getText().length(), 0));

                if (text.getText().length() == 3) {
                    txtSSN2.requestFocus();
                }
            }
        });

        txtSSN2 = new JTextField();
        txtSSN2.setColumns(2);
        txtSSN2.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField text = (JTextField) e.getSource();

                text.setText(format.limitLength(text.getText(), 2));
                text.setText(format.capitalizeProper(text.getText(), text.getText().length(), 0));

                if (text.getText().length() == 2) {
                    txtSSN3.requestFocus();
                }
            }
        });

        txtSSN3 = new JTextField();
        txtSSN3.setColumns(4);
        txtSSN3.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField text = (JTextField) e.getSource();

                text.setText(format.limitLength(text.getText(), 4));
                text.setText(format.capitalizeProper(text.getText(), text.getText().length(), 0));
            }
        });

        JPanel pSSN1 = new JPanel(new BorderLayout());
        pSSN1.add(lblSSN, BorderLayout.WEST);
        pSSN1.add(txtSSN1, BorderLayout.EAST);

        JPanel pSSN2 = new JPanel(new BorderLayout());
        pSSN2.add(txtSSN2);

        JPanel pSSN3 = new JPanel(new BorderLayout());
        pSSN3.add(txtSSN3);

        JPanel pSSN = new JPanel();
        pSSN.add(pSSN1);
        pSSN.add(pSSN2);
        pSSN.add(pSSN3);
        pSSN.setBounds(150, 215, 160, 30);

        //Add buttons
        btnAdd = new JButton("Add");
        btnAdd.setToolTipText("Add a new person to the database");
        btnAdd.addActionListener(new onClickAdd());

        btnFind = new JButton("Find");
        btnFind.setToolTipText("Search database with given criteria");
        btnFind.addActionListener(new onClickFind());

        btnClose = new JButton("Close");
        btnClose.setToolTipText("Close window without saving");
        btnClose.addActionListener(new onClickClose());

        JPanel pButtons = new JPanel(new GridLayout(3, 0, 0, 8));
        pButtons.add(btnAdd);
        pButtons.add(btnFind);
        pButtons.add(btnClose);
        pButtons.setBounds(45, 250, 200, 115);

        Icon iconReset = new ImageIcon(getClass().getResource("Reset Button Icon.png"));

        btnReset = new JButton(iconReset);
        btnReset.setBounds(253, 250, 32, 115);
        btnReset.setToolTipText("Clear all fields");
        btnReset.addActionListener(new onClickReset());

        f1.add(pFirstLast);
        f1.add(pAge);
        f1.add(pDOBmonth);
        f1.add(pDOBday);
        f1.add(pDOByear);
        f1.add(pAddress);
        f1.add(pCity);
        f1.add(pState);
        f1.add(pZip);
        f1.add(pGender);
        f1.add(pSSN);
        f1.add(pButtons);
        f1.add(btnReset);
        add(f1);

        setVisible(true);

    }

    private class onClickAdd implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            boolean saveable = true;
            //Make sure user did not leave any fields blank
            if (txtZip.getText().length() != 5) {
                saveable = false;
                JOptionPane.showMessageDialog(null, "Zip field must be 5 digits.");
            }
            if (txtSSN1.getText().length() != 3
                    || txtSSN2.getText().length() != 2
                    || txtSSN3.getText().length() != 4) {
                saveable = false;
                JOptionPane.showMessageDialog(null, "SSN fields must be filled xx-xxx-xxxx");
            }
            if (txtState.getText().length() != 2) {
                saveable = false;
                JOptionPane.showMessageDialog(null, "State field must be 2 letters.");
            }
            if (txtAge.getText().equals("") || Integer.parseInt(txtAge.getText()) < 0) {
                saveable = false;
                JOptionPane.showMessageDialog(null, "Invalid Age value.");
            }
            if (txtFirst.getText().equals("") || txtLast.getText().equals("")
                    || txtAddress.getText().equals("") || txtCity.getText().equals("")
                    || txtState.getText().equals("") || txtZip.getText().equals("")
                    || txtSSN1.getText().equals("") || txtSSN2.getText().equals("")
                    || txtSSN3.getText().equals("") || cmboDOBmonth.getSelectedItem().equals("")
                    || cmboDOBday.getSelectedItem().equals("") || cmboDOByear.getSelectedItem().equals("")) {
                saveable = false;
                JOptionPane.showMessageDialog(null, "Data fields cannot be blank."
                        + "\nRequired fields are highlighted in red.");
            }
            if (saveable) {
                //Method to add new entry to file
                createNewEntry();
            }
            else {
                requiredFields(true);
            }
        }

        public void createNewEntry() {
            //Set variables to text field values
            assignFieldsToVariables();

            //Append to file
            personalData.add(firstName + "#" + lastName + "#" + age + "#"
                    + DOBmonth + "/" + DOBday + "/" + DOByear + "#"
                    + address + "#" + city + "#" + state + "#" + zip + "#" + SSN + "#" + gender);
            try {
                FileWriter newEntry = new FileWriter("DatabaseInfo.txt", true);
                String text = personalData.toString()
                        .replace("[", "")
                        .replace("]", "");
                newEntry.write(text);
                newEntry.write(System.getProperty("line.separator"));
                newEntry.close();
                personalData.clear();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "There was an error writing to the file.");
            }
            JOptionPane.showMessageDialog(null, "New entry successfully created.");

            //Reset form fields
            clearFields();
            requiredFields(false);
        }
    }

    private class onClickFind implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            boolean findable = true;
            //Requires user to fill at least one data field.
            if (txtFirst.getText().equals("") && txtLast.getText().equals("")
                    && txtAge.getText().equals("") && cmboDOBmonth.getSelectedIndex() == 0
                    && cmboDOBday.getSelectedItem().equals("") && cmboDOByear.getSelectedIndex() == 0
                    && txtAddress.getText().equals("")
                    && txtCity.getText().equals("") && txtState.getText().equals("")
                    && txtZip.getText().equals("") && txtSSN1.getText().equals("")
                    && txtSSN2.getText().equals("") && txtSSN3.getText().equals("")
                    && cmboGender.getSelectedIndex() == 0) {
                findable = false;
                JOptionPane.showMessageDialog(null, "At least one field must be filled.");
            }
            if (!txtAge.getText().equals("") && Integer.parseInt(txtAge.getText()) <= 0) {
                findable = false;
                JOptionPane.showMessageDialog(null, "Invalid Date of Birth value.");
            }
            if (findable) {
                //Set variables to text field values
                assignFieldsToVariables();
                FindData search = new FindData(firstName, lastName, age, DOBmonth, DOBday, DOByear, address, city, state, zip, SSN, gender);
            }
        }
    }

    private class onClickClose implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    private class onClickReset implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            clearFields();
            requiredFields(false);
            JOptionPane.showMessageDialog(null, "The form has been cleared.");
        }
    }

    public void clearFields() {
        txtFirst.setText("");
        txtLast.setText("");
        txtAge.setText("");
        cmboDOBmonth.setSelectedIndex(0);
        cmboDOBday.setSelectedIndex(0);
        cmboDOByear.setSelectedIndex(0);
        txtAddress.setText("");
        txtCity.setText("");
        txtState.setText("");
        txtZip.setText("");
        txtSSN1.setText("");
        txtSSN2.setText("");
        txtSSN3.setText("");
        cmboGender.setSelectedIndex(0);
    }

    public void generateDOBValues(int index, boolean init) {
        int remembered = cmboDOBday.getSelectedIndex();
        int maxDays = 0;
        cmboDOBday.removeAllItems();

        if (init == true) {
            cmboDOByear.addItem("");
            for (int i = Year.now().getValue(); i > Year.now().getValue() - 110; i--) {
                cmboDOByear.addItem(Integer.toString(i));
            }
        }

        switch (index) {
        case 0:
        case 1:
            maxDays = 31;
            break;
        case 2: //Handle leap years
            if (cmboDOByear.getSelectedIndex() != 0) {
                int year = Integer.valueOf((String) cmboDOByear.getSelectedItem());
                if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
                    maxDays = 29;
                    break;
                }
                else {
                    maxDays = 28;
                    break;
                }
            }
            else {
                maxDays = 28;
                break;
            }
        case 3:
            maxDays = 31;
            break;
        case 4:
            maxDays = 30;
            break;
        case 5:
            maxDays = 31;
            break;
        case 6:
            maxDays = 30;
            break;
        case 7:
            maxDays = 31;
            break;
        case 8:
            maxDays = 31;
            break;
        case 9:
            maxDays = 30;
            break;
        case 10:
            maxDays = 31;
            break;
        case 11:
            maxDays = 30;
            break;
        case 12:
            maxDays = 31;
            break;
        }
        cmboDOBday.addItem("");
        for (int i = 1; i <= maxDays; i++) {
            if (i < 10) {
                cmboDOBday.addItem("0" + Integer.toString(i));
            }
            else {
                cmboDOBday.addItem(Integer.toString(i));
            }
        }
        if (remembered == 29 && cmboDOBmonth.getSelectedIndex() == 2) {
            cmboDOBday.setSelectedIndex(0);
        }
        else {
            cmboDOBday.setSelectedIndex(remembered);
        }
    }

    public void requiredFields(boolean HL) {
        Color red = Color.RED;
        Color def = Color.darkGray;
        if (HL) {
            //highlight req fields
            if (txtFirst.getText().equals("")) {
                lblFirst.setForeground(red);
            }
            else {
                lblFirst.setForeground(def);
            }

            if (txtLast.getText().equals("")) {
                lblLast.setForeground(red);
            }
            else {
                lblLast.setForeground(def);
            }

            if (cmboDOBmonth.getSelectedIndex() == 0) {
                lblDOBmonth.setForeground(red);
            }
            else {
                lblDOBmonth.setForeground(def);
            }

            if (cmboDOBday.getSelectedIndex() == 0) {
                lblDOBday.setForeground(red);
            }
            else {
                lblDOBday.setForeground(def);
            }

            if (cmboDOByear.getSelectedIndex() == 0) {
                lblDOByear.setForeground(red);
            }
            else {
                lblDOByear.setForeground(def);
            }

            if (txtAge.getText().equals("") || Integer.parseInt(txtAge.getText()) < 0) {
                lblAge.setForeground(red);
            }
            else {
                lblAge.setForeground(def);
            }

            if (txtAddress.getText().equals("")) {
                lblAddress.setForeground(red);
            }
            else {
                lblAddress.setForeground(def);
            }

            if (txtCity.getText().equals("")) {
                lblCity.setForeground(red);
            }
            else {
                lblCity.setForeground(def);
            }

            if (txtState.getText().equals("") || txtState.getText().length() != 2) {
                lblState.setForeground(red);
            }
            else {
                lblState.setForeground(def);
            }

            if (txtZip.getText().equals("") || txtZip.getText().length() != 5) {
                lblZip.setForeground(red);
            }
            else {
                lblZip.setForeground(def);
            }

            if (txtSSN1.getText().equals("") || txtSSN2.getText().equals("")
                    || txtSSN3.getText().equals("") || txtSSN1.getText().length() != 3
                    || txtSSN2.getText().length() != 2 || txtSSN3.getText().length() != 4) {
                lblSSN.setForeground(red);
            }
            else {
                lblSSN.setForeground(def);
            }
        }
        else {
            //unhighlight req fields
            lblFirst.setForeground(def);
            lblLast.setForeground(def);
            lblDOBmonth.setForeground(def);
            lblDOBday.setForeground(def);
            lblDOByear.setForeground(def);
            lblAge.setForeground(def);
            lblAddress.setForeground(def);
            lblCity.setForeground(def);
            lblState.setForeground(def);
            lblZip.setForeground(def);
            lblSSN.setForeground(def);
        }
    }

    public void assignFieldsToVariables() {
        firstName = txtFirst.getText();
        lastName = txtLast.getText();
        age = txtAge.getText();
        if (cmboDOBmonth.getSelectedIndex() == 0) {
            DOBmonth = "";
        }
        else {
            DOBmonth = String.valueOf(cmboDOBmonth.getSelectedIndex());
        }
        DOBday = (String) cmboDOBday.getSelectedItem();
        DOByear = (String) cmboDOByear.getSelectedItem();
        address = txtAddress.getText();
        city = txtCity.getText();
        state = txtState.getText();
        zip = txtZip.getText();
        SSN = txtSSN1.getText() + txtSSN2.getText() + txtSSN3.getText();
        gender = (String) cmboGender.getSelectedItem();
    }
}

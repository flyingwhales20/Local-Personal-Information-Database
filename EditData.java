package infodatabase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;

public class EditData extends JFrame {

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
    private JButton btnClose;
    private JButton btnSave;
    private JLabel lblFirst;
    private JLabel lblLast;
    private JLabel lblAge;
    private JLabel lblDOBmonth;
    private JLabel lblDOBday;
    private JLabel lblDOByear;
    private JLabel lblAddress;
    private JLabel lblCity;
    private JLabel lblState;
    private JLabel lblZip;
    private JLabel lblSSN;
    private JLabel lblGender;
    private JComboBox<String> cmboGender;
    private JCheckBox chkEditing = new JCheckBox("Enable Editing");
    Font chkFont = new Font("Serif", -1, 18);

    //Data variables
    private String firstName;
    private String lastName;
    private String age;
    private String DOBmonth;
    private String DOBday;
    private String DOByear;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String SSN1;
    private String SSN2;
    private String SSN3;
    private String gender;
    private JComboBox<String> cmboDOBmonth;
    private JComboBox<String> cmboDOBday;
    private JComboBox<String> cmboDOByear;
    private ArrayList<String> currentList = new ArrayList<>();
    private ArrayList<String> oldList = new ArrayList<>();

    EditData(String f, String l, String ag, String dob, String a, String c, String s, String z, String soc, String g) {
        super("Edit Data");
        JPanel f3 = new JPanel(null);
        generalGUI format = new generalGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(340, 410);
        setLocation(400, 100);
        setResizable(false);

        firstName = f;
        lastName = l;
        age = ag;
        String[] partsDOB = dob.split("/");
        DOBmonth = partsDOB[0];
        DOBday = partsDOB[1];
        DOByear = partsDOB[2];
        address = a;
        city = c;
        state = s;
        zip = z;
        //Parse out ssn to three grouping for 1,2,3
        SSN1 = soc.substring(0, 3);
        SSN2 = soc.substring(3, 5);
        SSN3 = soc.substring(5, 9);
        gender = g;

        chkEditing.setBounds(100, 10, 130, 30);
        chkEditing.setToolTipText("Check to edit data");
        chkEditing.setFont(chkFont);
        chkEditing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Enable only if editing is enabled
                txtFirst.setEditable(chkEditing.isSelected());
                txtLast.setEditable(chkEditing.isSelected());
                cmboDOBmonth.setEnabled(chkEditing.isSelected());
                cmboDOBday.setEnabled(chkEditing.isSelected());
                cmboDOByear.setEnabled(chkEditing.isSelected());
                txtAddress.setEditable(chkEditing.isSelected());
                txtCity.setEditable(chkEditing.isSelected());
                txtState.setEditable(chkEditing.isSelected());
                txtZip.setEditable(chkEditing.isSelected());
                txtSSN1.setEditable(chkEditing.isSelected());
                txtSSN2.setEditable(chkEditing.isSelected());
                txtSSN3.setEditable(chkEditing.isSelected());
                cmboGender.setEnabled(chkEditing.isSelected());
                btnSave.setEnabled(chkEditing.isSelected());
            }
        }
        );

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
        pFirstLast.setBounds(10, 50, 210, 40);

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
                generateDOBValues(cmboDOBmonth.getSelectedIndex());
            }
        });

        lblDOBday = new JLabel("Day: ");
        cmboDOBday = new JComboBox<>();
        cmboDOBday.addItem("");
        cmboDOBday.addActionListener(calcAge);

        lblDOByear = new JLabel("Year: ");
        cmboDOByear = new JComboBox<>();
        cmboDOByear.addItem("");
        for (int i = Year.now().getValue(); i > Year.now().getValue() - 110; i--) {
            cmboDOByear.addItem(Integer.toString(i));
        }
        cmboDOByear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cmboDOByear.getSelectedIndex() > 0) {
                    generateDOBValues(cmboDOBmonth.getSelectedIndex());
                }
            }
        });
        cmboDOByear.addActionListener(calcAge);

        JPanel pDOBmonth = new JPanel(new BorderLayout());
        pDOBmonth.add(lblDOBmonth, BorderLayout.NORTH);
        pDOBmonth.add(cmboDOBmonth, BorderLayout.SOUTH);
        pDOBmonth.setBounds(10, 100, 80, 40);

        JPanel pDOBday = new JPanel(new BorderLayout());
        pDOBday.add(lblDOBday, BorderLayout.NORTH);
        pDOBday.add(cmboDOBday, BorderLayout.SOUTH);
        pDOBday.setBounds(120, 100, 80, 40);

        JPanel pDOByear = new JPanel(new BorderLayout());
        pDOByear.add(lblDOByear, BorderLayout.NORTH);
        pDOByear.add(cmboDOByear, BorderLayout.SOUTH);
        pDOByear.setBounds(230, 100, 80, 40);

        //Add age
        lblAge = new JLabel("Age:");

        txtAge = new JTextField();
        txtAge.setEditable(false);

        JPanel pAge = new JPanel(new BorderLayout());
        pAge.add(lblAge, BorderLayout.NORTH);
        pAge.add(txtAge, BorderLayout.SOUTH);
        pAge.setBounds(230, 50, 40, 40);

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
        pAddress.setBounds(10, 150, 300, 40);

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
        pCity.setBounds(10, 210, 100, 20);

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
        pState.setBounds(120, 210, 65, 20);

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
        pZip.setBounds(195, 210, 85, 20);

        //Add gender
        lblGender = new JLabel("Gender: ");

        String[] listGender = {" ", "Male", "Female"};
        cmboGender = new JComboBox<>(listGender);
        cmboGender.setSelectedIndex(0);

        JPanel pGender = new JPanel();
        pGender.add(lblGender);
        pGender.add(cmboGender);
        pGender.setBounds(0, 250, 140, 40);

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
        pSSN.setBounds(150, 255, 160, 30);

        btnSave = new JButton("Save");
        btnSave.setBounds(10, 300, 315, 30);
        btnSave.setToolTipText("Save data");
        btnSave.addActionListener(new onClickSave());

        btnClose = new JButton("Close");
        btnClose.setBounds(10, 340, 315, 30);
        btnClose.setToolTipText("Close window without saving");
        btnClose.addActionListener(new onClickClose());

        //Set fields to values selected in previous window
        txtFirst.setText(firstName);
        txtLast.setText(lastName);
        txtAge.setText(age);
        cmboDOBmonth.setSelectedIndex(Integer.parseInt(DOBmonth));
        cmboDOBday.setSelectedItem(DOBday);
        cmboDOByear.setSelectedItem(DOByear);
        txtAddress.setText(address);
        txtCity.setText(city);
        txtState.setText(state);
        txtZip.setText(zip);
        //Parse out ssn to three grouping for 1,2,3
        txtSSN1.setText(SSN1);
        txtSSN2.setText(SSN2);
        txtSSN3.setText(SSN3);
        cmboGender.setSelectedItem(gender);

        //Set fields uneditable on start
        txtFirst.setEditable(false);
        txtLast.setEditable(false);
        cmboDOBmonth.setEnabled(false);
        cmboDOBday.setEnabled(false);
        cmboDOByear.setEnabled(false);
        txtAddress.setEditable(false);
        txtCity.setEditable(false);
        txtState.setEditable(false);
        txtZip.setEditable(false);
        txtSSN1.setEditable(false);
        txtSSN2.setEditable(false);
        txtSSN3.setEditable(false);
        cmboGender.setEnabled(false);
        btnSave.setEnabled(false);

        f3.add(chkEditing);
        f3.add(pFirstLast);
        f3.add(pAge);
        f3.add(pDOBmonth);
        f3.add(pDOBday);
        f3.add(pDOByear);
        f3.add(pAddress);
        f3.add(pCity);
        f3.add(pState);
        f3.add(pZip);
        f3.add(pGender);
        f3.add(pSSN);
        f3.add(btnSave);
        f3.add(btnClose);
        add(f3);

        setVisible(true);
    }

    public void updateEntry() {
        currentList.add(firstName + "#" + lastName + "#" + age + "#"
                + DOBmonth + "/" + DOBday + "/" + DOByear + "#" + address + "#"
                + city + "#" + state + "#" + zip + "#" + SSN1 + SSN2 + SSN3 + "#" + gender);
        String cList = currentList.toString().replace("[", "").replace("]", "");

        try {
            Scanner oldEntry = new Scanner(new File("DatabaseInfo.txt"));
            while (oldEntry.hasNextLine()) {
                oldList.add(oldEntry.nextLine());
            }
            oldEntry.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "There was an error reading from the file.");
        }
        try {
            FileWriter editEntry = new FileWriter("DatabaseInfo.txt");
            for (int i = 0; i < oldList.size(); i++) {
                if (oldList.get(i).equals(cList)) {
                    oldList.set(i, txtFirst.getText().trim() + "#" + txtLast.getText() + "#"
                            + txtAge.getText() + "#" + cmboDOBmonth.getSelectedIndex() + "/"
                            + cmboDOBday.getSelectedItem() + "/" + cmboDOByear.getSelectedItem() + "#"
                            + txtAddress.getText() + "#" + txtCity.getText() + "#" + txtState.getText() + "#"
                            + txtZip.getText() + "#" + txtSSN1.getText() + txtSSN2.getText() + txtSSN3.getText()
                            + "#" + cmboGender.getSelectedItem());
                }
                editEntry.write(oldList.get(i));
                editEntry.write(System.getProperty("line.separator"));

            }
            editEntry.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "There was an error writing to the file.");
        }
        JOptionPane.showMessageDialog(null, "Entry edited successfully.");
        dispose();
    }

    private class onClickClose implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    private class onClickSave implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            boolean saveable = true;
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
            if (!txtAge.getText().equals("") && Integer.parseInt(txtAge.getText()) < 0) {
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
                updateEntry();
            }
            else {
                requiredFields(true);
            }
        }
    }

    public void generateDOBValues(int index) {
        int remembered = cmboDOBday.getSelectedIndex();
        int maxDays = 0;
        cmboDOBday.removeAllItems();

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
}

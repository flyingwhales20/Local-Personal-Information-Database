package infodatabase;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

public class FindData extends JFrame {

    private JLabel lblHeader = new JLabel("First                Last                Age                Address                SSN");
    private JScrollPane scrollDisplay;
    private DefaultListModel<String> list = new DefaultListModel<>();
    private ArrayList<String> tempList = new ArrayList<>();
    private JButton btnClose = new JButton("Close");
    private boolean emptyData = true;

    //Data variables
    private String firstName = "";
    private String lastName = "";
    private String age = "";
    private String DOBmonth = "";
    private String DOBday = "";
    private String DOByear = "";
    private String address = "";
    private String city = "";
    private String state = "";
    private String zip = "";
    private String SSN = "";
    private String gender = "";

    String temp = "";
    Font italic = new Font("Serif", Font.ITALIC, 12);

    FindData(String f, String l, String ag, String DOBm, String DOBd, String DOBy, String a, String c, String s, String z, String soc, String g) {
        super("Search and Edit Data");
        JPanel f2 = new JPanel(null);
        setSize(340, 410);
        setLocation(400, 100);
        setResizable(false);

        firstName = f;
        lastName = l;
        age = ag;
        DOBmonth = DOBm;
        DOBday = DOBd;
        DOByear = DOBy;
        address = a;
        city = c;
        state = s;
        zip = z;
        SSN = soc;
        gender = g;
        
        //Because I said so
        if(gender.equals(" ")){
            gender = "";
        }
        //Update display pane
        loadMatchingData();

        lblHeader.setBounds(10, 10, 350, 20);
        lblHeader.setFont(italic);

        //temp stuff, to test list
        JList<String> content = new JList<>(list);
        content.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JList data = (JList) e.getSource();
                if (e.getClickCount() == 2 && data.getModel().getSize() != 0) {
                    //This checks the list to make sure it equals the selected value to pass 
                    //the data to the next window
                    for(int i=0; i< list.size(); i++){
                        if (list.get(i).equals(data.getSelectedValue().toString())) {
                            String[] parts = tempList.get(i).split("#");
                            EditData ed = new EditData(parts[0], parts[1], parts[2], parts[3], 
                                    parts[4], parts[5], parts[6], parts[7], parts[8], parts[9]);
                            dispose();
                        }
                    }
                }
            }
        }
        );
        content.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scrollDisplay = new JScrollPane(content);
        scrollDisplay.setBounds(10, 30, 315, 250);

        btnClose.setBounds(10, 290, 315, 30);
        btnClose.setToolTipText("Close window");
        btnClose.addActionListener(new onClickClose());

        f2.add(lblHeader);
        f2.add(scrollDisplay);
        f2.add(btnClose);

        add(f2);

        
        if(emptyData){
            JOptionPane.showMessageDialog(null, "No data matches your search. You will "
                            + "be returned to the main window.");
                    dispose();
        }
        else{
            setVisible(true);
        }
    }

    public void loadMatchingData() {

        try {
            Scanner loadEntry = new Scanner(new File("DatabaseInfo.txt"));
            while (loadEntry.hasNextLine()) {
                //Split line into corresponding values and store if mathing search
                temp = loadEntry.nextLine();
                String[] parts = temp.split("#");
                String[] partsDOB = parts[3].split("/");
                //Only add to list if values match search
                if (parts[0].contains(firstName) && parts[1].contains(lastName)
                        && parts[2].contains(age) && partsDOB[0].contains(DOBmonth) &&
                        partsDOB[1].contains(DOBday) && partsDOB[2].contains(DOByear) && parts[4].contains(address)
                        && parts[5].contains(city) && parts[6].contains(state)
                        && parts[7].contains(zip) && parts[8].contains(SSN)
                        && parts[9].contains(gender)) {
                    emptyData = false;
                    list.addElement(
                            parts[0] + " " + parts[1] + " "
                            + parts[2] + " " + parts[3] + " " + parts[4] + " "
                            + parts[5] + " " + parts[6] + " " + parts[7] + " "
                            + parts[8] + " " + parts[9]);
                    tempList.add(
                            parts[0] + "#" + parts[1] + "#"
                            + parts[2] + "#" + parts[3] + "#" + parts[4] + "#"
                            + parts[5] + "#" + parts[6] + "#" + parts[7] + "#"
                            + parts[8] + "#" + parts[9]);
                }
            }
            loadEntry.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "There was an error opening the file.");
            dispose();
        }
    }

    private class onClickClose implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
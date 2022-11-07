package com.example.sma3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class GymManagerController {

    @FXML
    private TextField firstName, lastName, locationField;
    @FXML
    private DatePicker dobField;
    @FXML
    private TextArea removeOut;
    @FXML
    private Button removeMember;

    MemberDatabase memberDatabase = new MemberDatabase();

    @FXML
    private void removeMemberButton(ActionEvent event) {
        String fname = firstName.getText();
        String lname = lastName.getText();
        String dobString = dobField.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        Date dob = new Date(dobString);
        Member member = memberDatabase.findMember(fname, lname, dob);
        if (member != null) {
            if (memberDatabase.remove(member)) {
                removeOut.setText(member.getName() + " removed.");
                System.out.println();

            }
        } else {
            removeOut.setText(fname + " " + lname + " is not in the database.");
        }

    }


    /**
     * conversion from textfield to Date, Location objects, and String objects
     */
    Date dob;
    Location gymLoc;
    String fName, lName;


    public void createMembership(ActionEvent e) {
        //try{
        fName = firstName.getText();
        lName = lastName.getText();
//        dob = new Date(dobField.getText());
        // }

    }

    @FXML
    protected void onHelloButtonClick() {
    }
}
package com.example.sma3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class GymManagerController {

    @FXML
    private TextField firstName, lastName;
    @FXML
    private TextField dateOfBirth, gymLocation;


    /**
     * conversion from textfield to Date, Location objects, and String objects
     */
    Date dob;
    Location gymLoc;
    String fName,lName;


    public void createMembership(ActionEvent e){
        //try{
            fName = firstName.getText();
            lName = lastName.getText();
            dob = new Date(dateOfBirth.getText());
       // }

    }
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Running Gym Manager...");
    }
}
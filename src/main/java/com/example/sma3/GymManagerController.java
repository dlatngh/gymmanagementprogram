package com.example.sma3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class GymManagerController {
    private static final double FAMILY_FEE = 29.99;             //  defines one time fee for family membership
    private static final double FAMILY_MONTHLY = 59.99;         //  defines monthly fee for family
    private static final int FAMILY_GUEST_PASS = 1;             //  defines how many guest passes for family membership
    private static final double PREMIUM_FEE = 0.0;              //  defines the one time fee for premium membership
    private static final double PREMIUM_MONTHLY = 29.99;        //  defines the monthly fee for the premium membership
    private static final int PREMIUM_GUEST_PASS = 3;            //  defines how many guest passes for premium membership

    @FXML
    protected RadioButton standard, family, premium;
    @FXML
    protected TextField fnameAdd, lnameAdd, locationFieldAdd,
            firstNameRemove, lastNameRemove,
            fnameM, lnameM, classNameM, instNameM, locationFieldM,
            fnameG, lnameG, classNameG, instNameG, locationFieldG;
    @FXML
    protected DatePicker dobFieldA, dobFieldR, dobFieldM, dobFieldG;
    @FXML
    protected TextArea displayOut;
    @FXML
    protected Button addMemberB, removeMemberB, checkInB, checkOutB, checkGuestB, checkOutGuestB, printB,
            sortNameB, sortExpB, sortLocB, sortPriceB, printSchedB, loadMemberB, loadClassB;
    MemberDatabase memberDatabase = new MemberDatabase();
    ClassSchedule classSchedule = new ClassSchedule();


    /**
     * Helper method to see if member is present in member list
     *
     * @param member       to search
     * @param fitnessClass to search in
     * @return true if found, false otherwise.
     */
    private boolean findInList(Member member, FitnessClass fitnessClass) {
        return fitnessClass.getMemberList().contains(member);
    }

    /**
     * Prints if member is successfully checked in
     * Prints all participating members of the class after.
     *
     * @param member       to check in
     * @param fitnessClass of the class to check into
     */
    private void printCheckInSuccess(Member member, FitnessClass fitnessClass) {
        String lineOne = member.getName() + " checked in " + fitnessClass.toString();
        String lineTwo = "- Participants -";
        displayOut.appendText("\n" + lineOne + "\n" + lineTwo + "\n");
        for (int i = 0; i < fitnessClass.getMemberList().size(); i++)
            displayOut.appendText("   "
                    + fitnessClass.getMemberList().get(i) + "\n");
        displayOut.appendText("\n");
    }

    /**
     * Prints the time conflict error message
     *
     * @param fitnessClass you're checking
     */
    public void printTimeConflict(FitnessClass fitnessClass) {
        displayOut.appendText("\n" + "Time conflict - " + fitnessClass.toString() + ", 0" +
                fitnessClass.getLocation().getZipCode() + ", " + fitnessClass.getLocation().getCounty() + "\n");
    }

    /**
     * Prints the location restriction error Message
     *
     * @param member   trying to check in
     * @param location that member is trying to check in to.
     */
    private void printStandardRestriction(Member member, Location location) {
        displayOut.appendText("\n" + member.getName() + " checking in " + location + ", 0" +
                location.getZipCode() + ", " + location.getCounty() +
                " - standard membership location restriction.\n");
    }

    /**
     * Prints not in the database error message
     *
     * @param fname     of the invalid member
     * @param lname     of the invalid member
     * @param dobString of the invalid member
     */
    public void printNotInDB(String fname, String lname, String dobString) {
        displayOut.appendText("\n" + fname + " " + lname + " " + dobString + " is not in the database.\n");
    }

    /**
     * Prints not checked in error message.
     *
     * @param name of member that's not checked in
     */
    public void printNotPresent(String name) {
        displayOut.appendText(name + " did not check in.\n");
    }

    /**
     * Prints when successfully checked out
     *
     * @param name of member that checked out
     */
    public void printCheckedOut(String name) {
        displayOut.appendText(name + " done with the class.\n");
    }

    /**
     * Prints standard membership guest error message
     */
    public void printStandardMembershipError() {
        displayOut.appendText("Standard membership - guest check-in is not allowed.\n");
    }

    /**
     * checks if location is the same
     *
     * @param fitnessClass class
     * @param member       to check in
     * @return true if yes false otherwise
     */
    private boolean isSameLocation(FitnessClass fitnessClass, Member member) {
        return member.getLocation().equals(fitnessClass.getLocation());
    }

    /**
     * Prints when guests are successfully checked out.
     *
     * @param name of member's guest.
     */
    public void printGuestCheckedOut(String name) {
        displayOut.appendText(name + " Guest done with the class.\n");
    }

    /**
     * Prints when guests try to check into a different location than its member's
     *
     * @param classLocation class location
     * @param classzipCode  classzipCode
     * @param classCounty   classCounty
     * @param x             members name
     */
    public void printGuestNoCheckIn(String classLocation, int classzipCode, String classCounty, Member x) {
        displayOut.appendText(x.getName() + " Guest checking in " + classLocation + ", 0" + classzipCode
                + ", " + classCounty + " - guest location restriction.\n");
    }

    /**
     * Prints if guest is successfully checked in.
     * Prints participating members as well if present.
     *
     * @param member's     guest
     * @param fitnessClass to check into
     */
    private void printGuestCheckInSuccess(Member member, FitnessClass fitnessClass) {
        displayOut.appendText(member.getName() + " (guest) checked in " + fitnessClass.toString() + "\n");
        if (!fitnessClass.getMemberList().isEmpty()) {
            displayOut.appendText("- Participants -\n");
            for (int i = 0; i < fitnessClass.getMemberList().size(); i++) {
                displayOut.appendText("   " + fitnessClass.getMemberList().get(i) + "\n");
            }
        }
        displayOut.appendText("- Guests -\n");
        for (int i = 0; i < fitnessClass.getGuestList().size(); i++) {
            displayOut.appendText("   " + fitnessClass.getGuestList().get(i) + "\n");
        }
        displayOut.appendText("\n");
    }

    /**
     * Prints depleted guest pass error message.
     *
     * @param name of member
     */
    private void printNoGuestPasses(String name) {
        displayOut.appendText(name + " ran out of guest pass.\n");
    }

    /**
     * Checks if member holds Family membership
     *
     * @param member to check
     * @return true if family, false otherwise.
     */
    public boolean isFamily(Member member) {
        return member instanceof Family;
    }

    /**
     * Checks if member holds Premium membership
     *
     * @param member to check
     * @return true if premium, false otherwise.
     */
    public boolean isPremium(Member member) {
        return member instanceof Premium;
    }

    /**
     * Checks if member holds default membership
     *
     * @param member to check
     * @return true if default, false otherwise.
     */
    public boolean isDefault(Member member) {
        return (!isPremium(member) && !isFamily(member));
    }

    /**
     * Prints the does not exist error message
     *
     * @param className  of the class
     * @param instructor of the class
     * @param location   of the class
     */
    public void printDNE(String className, String instructor, String location) {
        if (!classSchedule.isAtLocation(location, instructor, className))
            displayOut.appendText("\n" + className + " by " + instructor + " does not exist at " + location + "\n");
    }

    /**
     * Prints the invalid DOB error message
     *
     * @param dob that is invalid
     */
    public void printInvalidDOB(String dob) {
        displayOut.appendText("\n" + "DOB " + dob + ": invalid calendar date!\n");
    }

    /**
     * Prints the invalid Class error message
     *
     * @param className that is invalid
     */
    public void printInvalidClass(String className) {
        displayOut.appendText("\n" + className + " - class does not exist.\n");
    }

    /**
     * Prints the invalid instructor error message
     *
     * @param instructor that is invalid
     */
    public void printInvalidInstructor(String instructor) {
        displayOut.appendText("\n" + instructor + " - instructor does not exist.\n");
    }

    /**
     * clear the text field to re-enter for adding members
     *
     * @param event text field click
     */
    @FXML
    private void firstcleartextField(MouseEvent event) {
        fnameAdd.clear();
        fnameAdd.setStyle("-fx-text-fill: black;");
    }

    /**
     * clear the text field to re-enter for adding members
     *
     * @param event text field click
     */
    @FXML
    private void lastcleartextField(MouseEvent event) {
        lnameAdd.clear();
        lnameAdd.setStyle("-fx-text-fill: black;");
    }

    /**
     * adds member to memberlist
     *
     * @param e the event
     */
    @FXML
    private void addMemberButton(ActionEvent e) {
        if (fnameAdd.getText().isEmpty()) {
            fnameAdd.appendText("You must fill out a first name!");
            fnameAdd.setStyle("-fx-text-fill: red;");
        } else if (lnameAdd.getText().isEmpty()) {
            lnameAdd.appendText("You must fill out a last name!");
            lnameAdd.setStyle("-fx-text-fill: red;");
        } else {
            try {
                String fnameInput = fnameAdd.getText().replaceAll("\\s", "");
                String fname = fnameInput.substring(0, 1).toUpperCase() + fnameInput.substring(1);
                String lnameInput = lnameAdd.getText().replaceAll("\\s", "");
                String lname = lnameInput.substring(0, 1).toUpperCase() + lnameInput.substring(1);
                String dobString = dobFieldA.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                Date dob = new Date(dobString);
                String locationString = locationFieldAdd.getText().toUpperCase().replaceAll("\\s", "");
                if (!dob.isValid())
                    displayOut.appendText("DOB " + dob.dateToString(dob) + ": invalid calendar date!\n");
                else if (dob.isToday() || dob.isFuture())
                    displayOut.appendText("DOB " + dob.dateToString(dob) + ": cannot be today or a future date!\n");
                else if (dob.isMinor())
                    displayOut.appendText("DOB " + dob.dateToString(dob) + ": must be 18 or older to join!\n");
                else {
                    int currMonth = Date.getCurrMonth();
                    int currYear = Date.getCurrYear();
                    int expMonth = (currMonth + 3) % 12;
                    if (expMonth < currMonth) currYear++;
                    Date expire = new Date(expMonth + "/" + Date.getCurrDay() + "/" + currYear);
                    if (!expire.isValid())
                        displayOut.appendText("Expiration Date " + expire.dateToString(expire) +
                                ": invalid calendar date!\n");
                    else {
                        if (Location.isValidLoc(locationString)) {
                            Location location = Location.valueOf(locationString);
                            if (standard.isSelected()) {
                                Member member = new Member(fname, lname, dob, expire, location);
                                if (memberDatabase.add(member)) displayOut.appendText(member.getName() + " added.\n");
                                else displayOut.appendText(member.getName() + " is already in the database.\n");
                            } else if (family.isSelected()) {
                                Member member = new Family(fname, lname, dob, expire, location, FAMILY_FEE,
                                        FAMILY_MONTHLY, FAMILY_GUEST_PASS);
                                if (memberDatabase.add(member)) displayOut.appendText(member.getName() + " added.\n");
                                else displayOut.appendText(member.getName() + " is already in the database.\n");
                            } else if (premium.isSelected()) {
                                Member member = new Premium(fname, lname, dob, expire, location, PREMIUM_FEE,
                                        PREMIUM_MONTHLY, PREMIUM_GUEST_PASS);
                                if (memberDatabase.add(member)) displayOut.appendText(member.getName() + " added.\n");
                                else displayOut.appendText(member.getName() + " is already in the database.\n");
                            } else displayOut.appendText("You must specify membership type!\n");
                        } else displayOut.appendText(locationString + ": invalid location!\n");
                    }
                }
            } catch (NullPointerException | DateTimeParseException ex) {
                displayOut.appendText("Date cannot be empty or invalid!\n");
            }
        }
    }


    /**
     * clear the text field to re-enter for removing members
     *
     * @param event
     */
    @FXML
    private void removeFirstcleartextField(MouseEvent event) {
        firstNameRemove.clear();
        firstNameRemove.setStyle("-fx-text-fill: black;");
    }

    @FXML
    private void removeLastcleartextField(MouseEvent event) {
        lastNameRemove.clear();
        lastNameRemove.setStyle("-fx-text-fill: black;");
    }

    /**
     * removes member from member list
     *
     * @param event button click
     */
    @FXML
    private void removeMemberButton(ActionEvent event) {
        if (firstNameRemove.getText().isEmpty()) {
            firstNameRemove.appendText("You must fill out a first name!");
            firstNameRemove.setStyle("-fx-text-fill: red;");
        } else if (lastNameRemove.getText().isEmpty()) {
            lastNameRemove.appendText("You must fill out a last name!");
            lastNameRemove.setStyle("-fx-text-fill: red;");
        } else {
            try {
                String fnameInput = firstNameRemove.getText().replaceAll("\\s", "");
                String fname = fnameInput.substring(0, 1).toUpperCase() + fnameInput.substring(1);
                String lnameInput = lastNameRemove.getText().replaceAll("\\s", "");
                String lname = lnameInput.substring(0, 1).toUpperCase() + lnameInput.substring(1);
                String dobString = dobFieldR.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                Date dob = new Date(dobString);
                Member member = memberDatabase.findMember(fname, lname, dob);
                if (member != null) {
                    if (memberDatabase.remove(member)) displayOut.appendText(member.getName() + " removed." + "\n");
                } else displayOut.appendText(fname + " " + lname + " is not in the database." + "\n");
            } catch (NullPointerException | DateTimeParseException ex) {
                displayOut.appendText("Date cannot be empty or invalid!\n");
            }
        }
    }

    /**
     * clear the text field for fitness class regular members firstname/lastname
     *
     * @param event
     */
    @FXML
    private void checkInFirstcleartextField(MouseEvent event) {
        fnameM.clear();
        fnameM.setStyle("-fx-text-fill: black;");
    }

    @FXML
    private void checkInLastcleartextField(MouseEvent event) {
        lnameM.clear();
        lnameM.setStyle("-fx-text-fill: black;");

    }

    /**
     * checks in members to Fitness Class
     *
     * @param event button click
     */
    @FXML
    private void checkIn(ActionEvent event) {
        if (fnameM.getText().isEmpty()) {
            fnameM.appendText("You must fill out a first name!");
            fnameM.setStyle("-fx-text-fill: red;");
        } else if (lnameM.getText().isEmpty()) {
            lnameM.appendText("You must fill out a last name!");
            lnameM.setStyle("-fx-text-fill: red;");
        } else {
            try {
                String fname = fnameM.getText().replaceAll("\\s", "");
                String lname = lnameM.getText().replaceAll("\\s", "");
                String dobString = dobFieldM.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                String className = classNameM.getText().replaceAll("\\s", "");
                String instName = instNameM.getText().replaceAll("\\s", "");
                Date dob = new Date(dobString);
                if (Location.isValidLoc(locationFieldM.getText().replaceAll("\\s", ""))) {
                    Location location = Location.valueOf(locationFieldM.getText().toUpperCase().replaceAll("\\s", ""));
                    Member member = memberDatabase.findMember(fname, lname, dob);
                    FitnessClass fitnessClass = classSchedule.findClass(className, instName, location);
                    if (dob.isValid()) {
                        if (classSchedule.isValidClass(className)) {
                            if (classSchedule.isValidInstructor(instName)) {
                                if (classSchedule.isAtLocation(locationFieldM.getText(), instName,
                                        className)) {
                                    if (member != null) {
                                        if (member.getExpire().isFuture()) {
                                            if (classSchedule.canCheckInElseWhere(member, locationFieldM.getText())) {
                                                if (!findInList(member, fitnessClass)) {
                                                    if (!classSchedule.isConflict(fitnessClass, member)) {
                                                        if (classSchedule.checkIn(fitnessClass, member))
                                                            printCheckInSuccess(member, fitnessClass);
                                                    } else printTimeConflict(fitnessClass);
                                                } else displayOut.appendText(member.getName() +
                                                        " already checked in." + "\n");
                                            } else printStandardRestriction(member, location);
                                        } else displayOut.appendText(member.getName() + " " + dobString +
                                                " membership expired." + "\n");
                                    } else printNotInDB(fname, lname, dobString);
                                } else printDNE(className, instName, locationFieldM.getText());
                            } else printInvalidInstructor(instName);
                        } else printInvalidClass(className);
                    } else printInvalidDOB(dob.dateToString(dob));
                } else displayOut.appendText(locationFieldM.getText() + " - invalid location." + "\n");
            } catch (NullPointerException | DateTimeParseException ex) {
                displayOut.appendText("Date cannot be empty or invalid!\n");
            }
        }
    }

    /**
     * checks a member out of a Fitness Class
     *
     * @param event button click
     */
    @FXML
    private void checkOut(ActionEvent event) {
        if (fnameM.getText().isEmpty()) {
            fnameM.appendText("You must fill out a first name!");
            fnameM.setStyle("-fx-text-fill: red;");
        } else if (lnameM.getText().isEmpty()) {
            lnameM.appendText("You must fill out a last name!");
            lnameM.setStyle("-fx-text-fill: red;");
        } else {
            try {
                String fname = fnameM.getText().replaceAll("\\s", "");
                String lname = lnameM.getText().replaceAll("\\s", "");
                String dobString = dobFieldM.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                String className = classNameM.getText().replaceAll("\\s", "");
                String instName = instNameM.getText().replaceAll("\\s", "");
                Date dob = new Date(dobString);
                if (Location.isValidLoc(locationFieldM.getText().replaceAll("\\s", ""))) {
                    Location location = Location.valueOf(locationFieldM.getText().toUpperCase().replaceAll("\\s", ""));
                    Member member = memberDatabase.findMember(fname, lname, dob);
                    FitnessClass fitnessClass = classSchedule.findClass(className, instName, location);
                    if (dob.isValid()) {
                        if (classSchedule.isValidClass(className)) {
                            if (classSchedule.isValidInstructor(instName)) {
                                if (classSchedule.isAtLocation(locationFieldM.getText(), instName,
                                        className)) {
                                    if (member != null) {
                                        if (findInList(member, fitnessClass)) {
                                            if (classSchedule.checkOut(fitnessClass, member))
                                                printCheckedOut(member.getName());
                                            else printNotPresent(member.getName());
                                        } else printNotPresent(member.getName());
                                    } else printNotInDB(fname, lname, dobString);
                                } else printDNE(className, instName, locationFieldM.getText());
                            } else printInvalidInstructor(locationFieldM.getText());
                        } else printInvalidClass(className);
                    } else printInvalidDOB(dob.dateToString(dob));
                } else displayOut.appendText(locationFieldM.getText() + " - invalid location.");
            } catch (NullPointerException | DateTimeParseException ex) {
                displayOut.appendText("Date cannot be empty or invalid!\n");
            }
        }
    }

    /**
     * clear the text field for guestCheckin/guestCheckout
     *
     * @param event
     */
    @FXML
    private void guestCheckFirstcleartextField(MouseEvent event) {
        fnameG.clear();
        fnameG.setStyle("-fx-text-fill: black;");
    }

    @FXML
    private void guestCheckLastcleartextField(MouseEvent event) {
        lnameG.clear();
        lnameG.setStyle("-fx-text-fill: black;");
    }

    /**
     * Helper method to check out members
     *
     * @param event button click
     */
    @FXML
    private void guestCheckIn(ActionEvent event) {
        if (fnameG.getText().isEmpty()) {
            fnameG.appendText("You must fill out a first name!");
            fnameG.setStyle("-fx-text-fill: red;");
        } else if (lnameG.getText().isEmpty()) {
            lnameG.appendText("You must fill out a last name!");
            lnameG.setStyle("-fx-text-fill: red;");
        } else {
            try {
                String fname = fnameG.getText().replaceAll("\\s", "");
                String lname = lnameG.getText().replaceAll("\\s", "");
                String dobString = dobFieldG.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                String className = classNameG.getText().replaceAll("\\s", "");
                String instName = instNameG.getText().replaceAll("\\s", "");
                Date dob = new Date(dobString);
                String locationString = locationFieldG.getText().replaceAll("\\s", "").toUpperCase();
                Location location = Location.valueOf(locationString);
                Member member = memberDatabase.findMember(fname, lname, dob);
                FitnessClass fitnessClass = classSchedule.findClass(className, instName, location);
                if (isDefault(member)) printStandardMembershipError();
                else if (isSameLocation(fitnessClass, member)) {
                    if (member instanceof Premium) {
                        if (((Premium) member).getGuestPasses() > 0) {
                            classSchedule.checkGuest(fitnessClass, member);
                            ((Premium) member).useGuestPasses();
                            printGuestCheckInSuccess(member, fitnessClass);
                        } else printNoGuestPasses(member.getName());
                    } else if (member instanceof Family) {
                        if (((Family) member).getGuestPasses() > 0) {
                            classSchedule.checkGuest(fitnessClass, member);
                            ((Family) member).useGuestPass();
                            printGuestCheckInSuccess(member, fitnessClass);
                        } else printNoGuestPasses(member.getName());
                    }
                } else printGuestNoCheckIn(location.toString(), location.getZipCode(), location.getCounty(), member);
            } catch (NullPointerException | DateTimeParseException ex) {
                displayOut.appendText("Date cannot be empty or invalid!\n");
            }
        }
    }

    /**
     * Helper method to check out guest
     *
     * @param event button click
     */
    @FXML
    private void guestCheckOut(ActionEvent event) {
        if (fnameG.getText().isEmpty()) {
            fnameG.appendText("You must fill out a first name!");
            fnameG.setStyle("-fx-text-fill: red;");
        } else if (lnameG.getText().isEmpty()) {
            lnameG.appendText("You must fill out a last name!");
            lnameG.setStyle("-fx-text-fill: red;");
        } else {
            try {
                String fname = fnameG.getText().replaceAll("\\s", "");
                String lname = lnameG.getText().replaceAll("\\s", "");
                String dobString = dobFieldG.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                String className = classNameG.getText().replaceAll("\\s", "");
                String instName = instNameG.getText().replaceAll("\\s", "");
                String locationString = locationFieldG.getText().replaceAll("\\s", "").toUpperCase();
                Date dob = new Date(dobString);
                if (Location.isValidLoc(locationString)) {
                    Location location = Location.valueOf(locationString);
                    Member member = memberDatabase.findMember(fname, lname, dob);
                    FitnessClass fitnessClass = classSchedule.findClass(className, instName.toUpperCase(), location);
                    if (dob.isValid()) {
                        if (classSchedule.isValidClass(className)) {
                            if (classSchedule.isValidInstructor(instName)) {
                                if (classSchedule.isAtLocation(locationString, instName, className)) {
                                    if (member != null) {
                                        classSchedule.checkGuestOut(fitnessClass, member);
                                        printGuestCheckedOut(member.getName());
                                        if (member instanceof Family) ((Family) member).addGuestPass();
                                        else if (member instanceof Premium) ((Premium) member).addGuestPass();
                                    } else printNotInDB(fname, lname, dobString);
                                } else printDNE(className, instName, locationString);
                            } else printInvalidInstructor(instName);
                        } else printInvalidClass(className);
                    } else printInvalidDOB(dob.dateToString(dob));
                } else displayOut.appendText(locationString + " - invalid location!\n");
            } catch (NullPointerException | DateTimeParseException ex) {
                displayOut.appendText("Date cannot be empty or invalid!\n");
            }
        }
    }

    /**
     * print member by exp date in TextArea box
     *
     * @param est button click
     */
    @FXML
    private void sortByExpirationDate(ActionEvent est) {
        displayOut.appendText(memberDatabase.printByExpirationDate());
    }

    /**
     * print member list as is
     *
     * @param event button click
     */
    @FXML
    private void print(ActionEvent event) {

        displayOut.appendText(memberDatabase.print());
    }

    /**
     * Print member list sorted by membership fee
     *
     * @param event button click
     */
    @FXML
    private void printFee(ActionEvent event) {
        displayOut.appendText(memberDatabase.printFee());
    }

    /**
     * Print member list sorted by county
     *
     * @param event button click
     */
    @FXML
    private void sortByCounty(ActionEvent event) {

        displayOut.appendText(memberDatabase.printByCounty());
    }

    /**
     * Print member list sorted by last name
     *
     * @param event button click
     */
    @FXML
    private void sortByName(ActionEvent event) {
        displayOut.appendText(memberDatabase.printByName());
    }

    /**
     * Helper method to print the schedule
     *
     * @param event button click
     */
    @FXML
    private void displaySchedule(ActionEvent event) {
        displayOut.appendText(classSchedule.print());
    }

    /**
     * Loads the fitness class schedule
     *
     * @param event button click
     */
    @FXML
    private void loadSchedule(ActionEvent event) throws FileNotFoundException {
        classSchedule.loadSchedule();
        displayOut.appendText(classSchedule.loadSchedule());
    }

    /**
     * Loads the member list
     *
     * @param event button click
     */
    @FXML
    private void loadMemberList(ActionEvent event) throws FileNotFoundException {
        displayOut.appendText(memberDatabase.loadMemberList());
    }
}
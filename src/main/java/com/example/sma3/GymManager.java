package com.example.sma3;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Processes the command lines entered on the IDE console and displays on the console.
 * Instance of the class processes a single command line, and a sequence of command lines in batch.
 * Displays "Gym Manager running..." when it starts running.
 * Continuously processes the command until the "Q" command is read.
 * Before stopping, displays "Gym Manager terminated.".
 *
 * @author David Kim, Sooho Lim
 */
public class GymManager {
    private static final String ADD = "A";                      //  defines command to add a member with family membership to member database.
    private static final String CANCEL = "R";                   //  defines command to cancel membership and remove member
    private static final String DISPLAY_DATABASE = "P";         //  defines command to display database
    private static final String SORT_BY_COUNTY = "PC";          //  defines command to sort and display database by county
    private static final String SORT_BY_LAST_NAME = "PN";       //  defines command to sort and display database by last name
    private static final String SORT_BT_EXP_DATE = "PD";        //  defines command to sort and display database by exp date
    private static final String DISPLAY_SCHEDULE = "S";         //  defines command to display class schedule
    private static final String CHECK_IN = "C";                 //  defines command for members to check in
    private static final String QUIT_PROGRAM = "Q";             //  defines command to quit the program
    private static final String LOAD_SCHEDULE = "LS";           //  defines command to load fitness class schedule from classSchedule.txt to ClassSchedule
    private static final String LOAD_MEMBER = "LM";             //  defines command to lead member from memberList.txt to MemberDatabase
    private static final String ADD_FAMILY = "AF";              //  defines command to add a member with family membership
    private static final String ADD_PREMIUM = "AP";             //  defines command to add a member with premium membership
    private static final String PRINT_FEE = "PF";               //  defines command to print list of members with membership fees
    private static final String CHECK_GUEST = "CG";             //  defines command to check in family guest for fitness class
    private static final String DONE = "D";                     //  defines command to checking out
    private static final String DONE_GUEST = "DG";              //  defines command to checking out for guests
    private static final double FAMILY_FEE = 29.99;             //  defines one time fee for family membership
    private static final double FAMILY_MONTHLY = 59.99;         //  defines monthly fee for family
    private static final int FAMILY_GUEST_PASS = 1;             //  defines how many guest passes for family membership
    private static final double PREMIUM_FEE = 0.0;              //  defines the one time fee for premium membership
    private static final double PREMIUM_MONTHLY = 29.99;        //  defines the monthly fee for the premium membership
    private static final int PREMIUM_GUEST_PASS = 3;            //  defines how many guest passes for premium membership

    MemberDatabase memberDatabase = new MemberDatabase();
    ClassSchedule classSchedule = new ClassSchedule();

    /**
     * Prints if member is successfully checked in
     * Prints all participating members of the class after.
     *
     * @param member       to check in
     * @param fitnessClass of the class to check into
     */
    private void printCheckInSuccess(Member member, FitnessClass fitnessClass) {
        System.out.println(member.getName() + " checked in " + fitnessClass.toString());
        System.out.println("- Participants -");
        for (int i = 0; i < fitnessClass.getMemberList().size(); i++) {
            System.out.println("   " + fitnessClass.getMemberList().get(i));
        }
        System.out.println();
    }

    /**
     * Prints if guest is successfully checked in.
     * Prints participating members as well if present.
     *
     * @param member's     guest
     * @param fitnessClass to check into
     */
    private void printGuestCheckInSuccess(Member member, FitnessClass fitnessClass) {
        System.out.println(member.getName() + " (guest) checked in " + fitnessClass.toString());
        if (!fitnessClass.getMemberList().isEmpty()) {
            System.out.println("- Participants -");
            for (int i = 0; i < fitnessClass.getMemberList().size(); i++) {
                System.out.println("   " + fitnessClass.getMemberList().get(i));
            }
        }
        System.out.println("- Guests -");
        for (int i = 0; i < fitnessClass.getGuestList().size(); i++) {
            System.out.println("   " + fitnessClass.getGuestList().get(i));
        }
        System.out.println();
    }

    private boolean isSameLocation(FitnessClass fitnessClass, Member member) {
        return member.getLocation().equals(fitnessClass.getLocation());
    }

    /**
     * Prints the location restriction error Message
     *
     * @param member   trying to check in
     * @param location that member is trying to check in to.
     */
    private void printStandardRestriction(Member member, Location location) {
        System.out.println(member.getName() + " checking in " +
                location + ", 0" + location.getZipCode() + ", " +
                location.getCounty() + " - standard membership location restriction.");
    }

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
     * Prints the time conflict error message
     *
     * @param fitnessClass you're checking
     */
    public void printTimeConflict(FitnessClass fitnessClass) {
        System.out.println("Time conflict - " + fitnessClass.toString()
                + ", 0" + fitnessClass.getLocation().getZipCode() + ", "
                + fitnessClass.getLocation().getCounty());
    }

    /**
     * Prints the invalid DOB error message
     *
     * @param dob that is invalid
     */
    public void printInvalidDOB(String dob) {
        System.out.println("DOB " + dob + ": invalid calendar date!");
    }

    /**
     * Prints the invalid Class error message
     *
     * @param className that is invalid
     */
    public void printInvalidClass(String className) {
        System.out.println(className + " - class does not exist.");
    }

    /**
     * Prints the invalid instructor error message
     *
     * @param instructor that is invalid
     */
    public void printInvalidInstructor(String instructor) {
        System.out.println(instructor + " - instructor does not exist.");
    }

    /**
     * Prints the does not exist error message
     *
     * @param className  of the class
     * @param instructor of the class
     * @param location   of the class
     */
    public void printDNE(String className, String instructor, String location) {
        if (!classSchedule.isAtLocation(location, instructor, className)) {
            System.out.println(className + " by " + instructor + " does not exist at " + location);
        }
    }

    /**
     * Prints standard membership guest error message
     */
    public void printStandardMembershipError() {
        System.out.println("Standard membership - guest check-in is not allowed.");
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
        System.out.println(x.getName() + " Guest checking in " + classLocation + ", 0" + classzipCode
                + ", " + classCounty + " - guest location restriction.");
    }

    /**
     * Prints depleted guest pass error message.
     *
     * @param name of member
     */
    private void printNoGuestPasses(String name) {
        System.out.println(name + " ran out of guest pass.");
    }

    /**
     * Prints not in the database error message
     *
     * @param fname     of the invalid member
     * @param lname     of the invalid member
     * @param dobString of the invalid member
     */
    public void printNotInDB(String fname, String lname, String dobString) {
        System.out.println(fname + " " + lname + " " + dobString + " is not in the database.");
    }

    /**
     * Prints not checked in error message.
     *
     * @param name of member that's not checked in
     */
    public void printNotPresent(String name) {
        System.out.println(name + " did not check in.");
    }

    /**
     * Prints when successfully checked out
     *
     * @param name of member that checked out
     */
    public void printCheckedOut(String name) {
        System.out.println(name + " done with the class.");
    }

    /**
     * Prints when guests are successfully checked out.
     *
     * @param name of member's guest.
     */
    public void printGuestCheckedOut(String name) {
        System.out.println(name + " Guest done with the class.");
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
     * Helper method to add member to the database
     *
     * @param param member to add
     */
    private void addMember(String param) {
        String[] memberParam = param.split(" ");
        String fname = memberParam[1];
        String lname = memberParam[2];
        String dobString = memberParam[3];
        String locationString = memberParam[4];
        assert dobString != null;
        Date dob = new Date(dobString);
        if (!dob.isValid()) {
            System.out.println("DOB " + dob.dateToString(dob) + ": invalid calendar date!");
        } else if (dob.isToday() || dob.isFuture()) {
            System.out.println("DOB " + dob.dateToString(dob) + ": cannot be today or a future date!");
        } else if (dob.isMinor()) {
            System.out.println("DOB " + dob.dateToString(dob) + ": must be 18 or older to join!");
        } else {
            int currMonth = Date.getCurrMonth();
            int currYear = Date.getCurrYear();
            int expMonth = (currMonth + 3) % 12;
            if (expMonth < currMonth) {
                currYear++;
            }
            Date expire = new Date(expMonth + "/" + Date.getCurrDay() + "/" + currYear);
            if (!expire.isValid()) {
                System.out.println("Expiration Date " + expire.dateToString(expire) + ": invalid calendar date!");
            } else {
                String locationUpper = locationString.toUpperCase();
                if (Location.isValidLoc(locationString)) {
                    Location location = Location.valueOf(locationUpper);
                    Member member = new Member(fname, lname, dob, expire, location);
                    if (memberDatabase.add(member)) {
                        System.out.println(member.getName() + " added.");
                    } else {
                        System.out.println(member.getName() + " is already in the database.");
                    }
                } else
                    System.out.println(locationString + ": invalid location!");
            }
        }
    }

    /**
     * Helper method for the cancel membership command
     *
     * @param param of member to find and cancel
     */
    private void removeMember(String param) {
        String[] memParam = param.split(" ");
        String fname = memParam[1];
        String lname = memParam[2];
        String dobString = memParam[3];
        Date dob = new Date(dobString);
        Member member = memberDatabase.findMember(fname, lname, dob);
        if (member != null) {
            if (memberDatabase.remove(member)) {
                System.out.println(member.getName() + " removed.");
                System.out.println();

            }
        } else {
            System.out.println(fname + " " + lname + " is not in the database.");
        }
    }

    /**
     * Helper method for the display database command.
     */
    private void displayDatabase() {
        memberDatabase.print();
    }

    /**
     * Helper method for the sort by county command.
     */
    private void sortByCounty() {
        memberDatabase.printByCounty();
    }

    /**
     * Helper method for the sort by name command
     */
    private void sortByLastName() {
        memberDatabase.printByName();
    }

    /**
     * Helper method for the sort by exp date command
     */
    private void sortByExpDate() {
        memberDatabase.printByExpirationDate();
    }

    /**
     * Helper method to print the schedule
     */
    private void displaySchedule() {
        classSchedule.print();
        System.out.println();
    }

    /**
     * Helper method for the check in command.
     *
     * @param param of the member to check in.
     */
    private void checkIn(String param) {
        String[] cParam = param.split(" ");
        Date dob = new Date(cParam[6]);
        if (Location.isValidLoc(cParam[3])) {
            Location location = Location.valueOf(cParam[3].toUpperCase());
            Member member = memberDatabase.findMember(cParam[4], cParam[5], dob);
            FitnessClass fitnessClass = classSchedule.findClass(cParam[1], cParam[2].toUpperCase(), location);
            if (dob.isValid()) {
                if (classSchedule.isValidClass(cParam[1])) {
                    if (classSchedule.isValidInstructor(cParam[2])) {
                        if (classSchedule.isAtLocation(cParam[3], cParam[2], cParam[1])) {
                            if (member != null) {
                                if (member.getExpire().isFuture()) {
                                    if (classSchedule.canCheckInElseWhere(member, cParam[3])) {
                                        if (!findInList(member, fitnessClass)) {
                                            if (!classSchedule.isConflict(fitnessClass, member)) {
                                                if (classSchedule.checkIn(fitnessClass, member))
                                                    printCheckInSuccess(member, fitnessClass);
                                            } else
                                                printTimeConflict(fitnessClass);
                                        } else
                                            System.out.println(member.getName() + " already checked in.");
                                    } else
                                        printStandardRestriction(member, location);
                                } else
                                    System.out.println(member.getName() + " " + cParam[6] + " membership expired.");
                            } else
                                printNotInDB(cParam[4], cParam[5], cParam[6]);
                        } else
                            printDNE(cParam[1], cParam[2], cParam[3]);
                    } else
                        printInvalidInstructor(cParam[2]);
                } else
                    printInvalidClass(cParam[1]);
            } else
                printInvalidDOB(dob.dateToString(dob));
        } else
            System.out.println(cParam[3] + " - invalid location.");
    }

    /**
     * Helper method to load memberdatabase
     */
    private void loadMember() throws FileNotFoundException {
        memberDatabase.loadMemberList();
    }

    /**
     * Helper method to display database with fees
     */
    private void displayDatabaseFee() {
        memberDatabase.printFee();
    }

    /**
     * Helper method to load schedule
     */
    private void loadSchedule() throws FileNotFoundException {
        classSchedule.loadSchedule();
    }

    /**
     * Helper method to add member with family membership
     *
     * @param param of command
     */
    private void addFamily(String param) {
        String[] memberParam = param.split(" ");
        String fname = memberParam[1];
        String lname = memberParam[2];
        String dobString = memberParam[3];
        String locationString = memberParam[4];
        assert dobString != null;
        Date dob = new Date(dobString);
        if (!dob.isValid()) {
            System.out.println("DOB " + dob.dateToString(dob) + ": invalid calendar date!");
        } else if (dob.isToday() || dob.isFuture()) {
            System.out.println("DOB " + dob.dateToString(dob) + ": cannot be today or future date!");
        } else if (dob.isMinor()) {
            System.out.println("DOB " + dob.dateToString(dob) + ": must be 18 or older to join!");
        } else {
            int currMonth = Date.getCurrMonth();
            int currYear = Date.getCurrYear();
            int expMonth = (currMonth + 3) % 12;
            if (expMonth < currMonth) {
                currYear++;
            }
            Date expire = new Date(expMonth + "/" + Date.getCurrDay() + "/" + currYear);
            if (!expire.isValid()) {
                System.out.println("Expiration Date " + expire.dateToString(expire) + ": invalid calendar date!");
            } else {
                String locationUpper = locationString.toUpperCase();
                if (Location.isValidLoc(locationString)) {
                    Location location = Location.valueOf(locationUpper);
                    Member member = new Family(fname, lname, dob, expire, location, FAMILY_FEE,
                            FAMILY_MONTHLY, FAMILY_GUEST_PASS);
                    if (memberDatabase.add(member)) {
                        System.out.println(member.getName() + " added.");
                    } else {
                        System.out.println(member.getName() + " already in the database.");
                    }
                } else
                    System.out.println(locationString + ": invalid location!");
            }
        }
    }

    /**
     * Helper method to add member with Premium membership
     *
     * @param param of command
     */
    private void addPremium(String param) {
        String[] memberParam = param.split(" ");
        String fname = memberParam[1];
        String lname = memberParam[2];
        String dobString = memberParam[3];
        String locationString = memberParam[4];
        assert dobString != null;
        Date dob = new Date(dobString);
        if (!dob.isValid()) {
            System.out.println("DOB " + dob.dateToString(dob) +
                    ": invalid calendar date!");
        } else if (dob.isToday() || dob.isFuture()) {
            System.out.println("DOB " + dob.dateToString(dob) +
                    ": cannot be today or future date!");
        } else if (dob.isMinor()) {
            System.out.println("DOB " + dob.dateToString(dob) +
                    ": must be 18 or older to join!");
        } else {
            int currMonth = Date.getCurrMonth();
            int currYear = Date.getCurrYear();
            int expMonth = (currMonth + 3) % 12;
            if (expMonth < currMonth) {
                currYear++;
            }
            Date expire = new Date(expMonth + "/" + Date.getCurrDay() + "/" + currYear);
            if (!expire.isValid()) {
                System.out.println("Expiration Date " + expire.dateToString(expire)
                        + ": invalid calendar date!");
            } else {
                String locationUpper = locationString.toUpperCase();
                if (Location.isValidLoc(locationString)) {
                    Location location = Location.valueOf(locationUpper);
                    Member member = new Premium(fname, lname, dob, expire, location,
                            PREMIUM_FEE, PREMIUM_MONTHLY, PREMIUM_GUEST_PASS);
                    if (memberDatabase.add(member)) {
                        System.out.println(member.getName() + " added.");
                    } else {
                        System.out.println(member.getName() +
                                " already in the database.");
                    }
                } else
                    System.out.println(locationString + ": invalid location!");
            }
        }
    }

    /**
     * Helper method to check in a guest
     */
    private void checkGuest(String param) {
        String[] cParam = param.split(" ");
        String className = cParam[1];
        String instructorName = cParam[2];
        String classTown = cParam[3];
        classTown = classTown.toUpperCase();
        String memberFirstName = cParam[4];
        String memberLastName = cParam[5];
        String memberDOB = cParam[6];
        Date dob = new Date(memberDOB);
        Location location = Location.valueOf(classTown);
        Member member = memberDatabase.findMember(memberFirstName, memberLastName, dob);
        FitnessClass instructorsClass = classSchedule.findClass(className, instructorName, location);
        if (isDefault(member)) {
            printStandardMembershipError();
        } else if (isSameLocation(instructorsClass, member)) {
            if (member instanceof Premium) {
                if (((Premium) member).getGuestPasses() > 0) {
                    classSchedule.checkGuest(instructorsClass, member);
                    ((Premium) member).useGuestPasses();
                    printGuestCheckInSuccess(member, instructorsClass);
                } else {
                    printNoGuestPasses(member.getName());
                }
            } else if (member instanceof Family) {
                if (((Family) member).getGuestPasses() > 0) {
                    classSchedule.checkGuest(instructorsClass, member);
                    ((Family) member).useGuestPass();
                    printGuestCheckInSuccess(member, instructorsClass);
                } else {
                    printNoGuestPasses(member.getName());
                }
            }
        } else {
            printGuestNoCheckIn(location.toString(), location.getZipCode(), location.getCounty(), member);
        }
    }

    /**
     * Helper method to check out members
     *
     * @param param of command
     */
    private void done(String param) {
        String[] dParam = param.split(" ");
        Date dob = new Date(dParam[6]);
        if (Location.isValidLoc(dParam[3])) {
            Location location = Location.valueOf(dParam[3].toUpperCase());
            Member member = memberDatabase.findMember(dParam[4], dParam[5], dob);
            FitnessClass fitnessClass = classSchedule.findClass(dParam[1], dParam[2].toUpperCase(), location);
            if (dob.isValid()) {
                if (classSchedule.isValidClass(dParam[1])) {
                    if (classSchedule.isValidInstructor(dParam[2])) {
                        if (classSchedule.isAtLocation(dParam[3], dParam[2], dParam[1])) {
                            if (member != null) {
                                if (findInList(member, fitnessClass)) {
                                    if (classSchedule.checkOut(fitnessClass, member))
                                        printCheckedOut(member.getName());
                                    else
                                        printNotPresent(member.getName());
                                } else
                                    printNotPresent(member.getName());
                            } else
                                printNotInDB(dParam[4], dParam[5], dParam[6]);
                        } else
                            printDNE(dParam[1], dParam[2], dParam[3]);
                    } else
                        printInvalidInstructor(dParam[2]);
                } else
                    printInvalidClass(dParam[1]);
            } else
                printInvalidDOB(dob.dateToString(dob));
        } else
            System.out.println(dParam[3] + " - invalid location.");
    }

    /**
     * Helper method to check out guest
     *
     * @param param of command
     */
    private void doneGuest(String param) {
        String[] dgParam = param.split(" ");
        String className = dgParam[1];
        String instructor = dgParam[2];
        String locationString = dgParam[3];
        String fname = dgParam[4];
        String lname = dgParam[5];
        String dobString = dgParam[6];
        Date dob = new Date(dobString);
        if (Location.isValidLoc(locationString)) {
            Location location = Location.valueOf(locationString.toUpperCase());
            Member member = memberDatabase.findMember(fname, lname, dob);
            FitnessClass fitnessClass = classSchedule.findClass(className, instructor.toUpperCase(), location);
            if (dob.isValid()) {
                if (classSchedule.isValidClass(className)) {
                    if (classSchedule.isValidInstructor(instructor)) {
                        if (classSchedule.isAtLocation(locationString, instructor, className)) {
                            if (member != null) {
                                classSchedule.checkGuestOut(fitnessClass, member);
                                printGuestCheckedOut(member.getName());
                                if (member instanceof Family) {
                                    ((Family) member).addGuestPass();
                                } else if (member instanceof Premium) {
                                    ((Premium) member).addGuestPass();
                                }

                            } else
                                printNotInDB(fname, lname, dobString);
                        } else
                            printDNE(className, instructor, locationString);
                    } else
                        printInvalidInstructor(instructor);
                } else
                    printInvalidClass(className);
            } else
                printInvalidDOB(dob.dateToString(dob));
        } else
            System.out.println(locationString + " - invalid location!");
    }

    /**
     * Continuously reads the command lines.
     */
    public void run() throws FileNotFoundException {
        System.out.println("Gym Manager running...");
        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {
            String command = input.next();
            if (command.equals(QUIT_PROGRAM)) {
                System.out.println("Gym Manager terminated.");
                return;
            }
            String commandParam = input.nextLine();
            switch (command) {
                case ADD -> this.addMember(commandParam);
                case CANCEL -> this.removeMember(commandParam);
                case DISPLAY_DATABASE -> this.displayDatabase();
                case SORT_BY_COUNTY -> this.sortByCounty();
                case SORT_BY_LAST_NAME -> this.sortByLastName();
                case SORT_BT_EXP_DATE -> this.sortByExpDate();
                case DISPLAY_SCHEDULE -> this.displaySchedule();
                case CHECK_IN -> this.checkIn(commandParam);
                case LOAD_MEMBER -> this.loadMember();
                case PRINT_FEE -> this.displayDatabaseFee();
                case LOAD_SCHEDULE -> this.loadSchedule();
                case ADD_FAMILY -> this.addFamily(commandParam);
                case ADD_PREMIUM -> this.addPremium(commandParam);
                case CHECK_GUEST -> this.checkGuest(commandParam);
                case DONE -> this.done(commandParam);
                case DONE_GUEST -> this.doneGuest(commandParam);
                default -> System.out.println(command + " is an invalid command!");
            }
        }
    }
}


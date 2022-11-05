package com.example.sma3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Array-based data structure to hold the list of members.
 * New members are always added to the end of the array.
 * Initial capacity of 4 and automatically grows by 4 whenever full.
 * Does not decrease in capacity.
 * Provides methods for managing the list of members.
 *
 * @author David Kim, Sooho Lim.
 */
public class MemberDatabase {

    private static final int NOT_FOUND = -1;    //  value to return if member is not found
    private int size = 4;       // size of the array
    private Member[] mlist = new Member[size]; // array for the database

    /**
     * Default constructor. Creates a mlist object of default capacity.
     */
    public MemberDatabase() {

    }

    /**
     * Searches for a member in the list and returns the index if found.
     * If not found, returns "-1".
     *
     * @param member to find.
     * @return index if member is found, -1 otherwise.
     */
    private int find(Member member) {
        for (int i = 0; i < mlist.length; i++) {
            if (member.equals(mlist[i])) {
                return i;
            }
        }
        return NOT_FOUND;
    }


    /**
     * Grows the capacity of the Member Database if needed.
     */
    private void grow() {
        Member[] newList = new Member[mlist.length + size];
        System.arraycopy(mlist, 0, newList, 0, mlist.length);
        mlist = newList;
    }

    /**
     * Helper method to find the member from its instance variables in the mlist.
     *
     * @param fname of the member
     * @param lname of the member
     * @param dob   of the member
     * @return member object of the corresponding member
     */
    public Member findMember(String fname, String lname, Date dob) {
        Member targetMember = null;
        for (Member member : mlist) {
            if (member != null) {
                if (member.getFName().equals(fname) && member.getLName().equals(lname) && member.getDob().equals(dob)) {
                    targetMember = member;
                }
            }
        }
        return targetMember;
    }

    /**
     * Adds a member to the Database
     *
     * @param member to add
     * @return true if successfully added, false if otherwise.
     */
    public boolean add(Member member) {
        if (find(member) != NOT_FOUND) {
            return false;
        } else {

            for (int i = 0; i < mlist.length; i++) {
                if (mlist[i] == null) {                // look for an empty index in array
                    mlist[i] = member;                 //  add the member
                    break;
                }
            }
            if (mlist[mlist.length - 1] != null) {
                grow();
            }
        }
        return true;
    }

    /**
     * Removes a member from the list.
     * Maintains the relative order of the members in the list after the remove.
     *
     * @param member to remove
     * @return true if successfully removed, false otherwise
     */
    public boolean remove(Member member) {
        if (find(member) != NOT_FOUND) {            //  if found (index isn't -1)
            int idxOfTarget = find(member);
            mlist[idxOfTarget] = null;         // delete the target
            for (int i = idxOfTarget + 1; i < mlist.length; i++) {
                mlist[i - 1] = mlist[i];
                mlist[i] = null;               //shift elements to maintain relative order.
            }
            return true;
        }
        return false;
    }

    /**
     * Prints the Member Data base as is
     */
    public void print() {
        if (mlist[0] == null) {
            System.out.println("Member database is empty!");
        } else {
            System.out.println("-list of members-");
            for (Member member : mlist) {
                if (member != null) {
                    System.out.println(member);
                }
            }
            System.out.println("-end of list-");
            System.out.println();
        }
    }

    /**
     * prints the Member database with fees
     */
    public void printFee() {
        if (mlist[0] == null) {
            System.out.println("Member database is empty!");
        } else {
            System.out.println();
            System.out.println("-list of members with membership fees-");
            for (Member member : mlist) {
                if (member != null) {
                    System.out.println(member + ", Membership fee: $" + member.membershipFee());
                }
            }
            System.out.println("-end of list-");
            System.out.println();
        }
    }

    /**
     * Prints the Member Data base sorted by county and then zipcode
     */
    public void printByCounty() {
        Member[] memberList = mlist;
        int n = memberList.length;
        if (mlist[0] == null) {
            System.out.println("Member database is empty!");
        } else {
            System.out.println();
            System.out.println("-list of members sorted by county and zipcode-");

            for (int i = 0; i < n - 1; i++) {
                for (int k = 0; k < n - i - 1; k++) {
                    if (memberList[k + 1] == null) {
                        continue;
                    }
                    //String firstPosMember = memberList[k].getLocation().getCounty().toLowerCase();
                    //String secondPosMember = memberList[k + 1].getLocation().getCounty().toLowerCase();
                    //System.out.println("county Names: " + firstPosMember + "AND " + secondPosMember);
                    //int pos = 0

                    //int firstPosGoFirst = firstPosMember.compareTo(secondPosMember);

                    if (memberList[k].getLocation().getCounty().toLowerCase().compareTo(memberList[k + 1].getLocation().getCounty().toLowerCase()) > 0) {
                        Member temp = memberList[k];
                        memberList[k] = memberList[k + 1];
                        memberList[k + 1] = temp;
                    }
                }
            }
            for (int i = 0; i < n - 1; i++) {
                for (int k = 0; k < n - i - 1; k++) {
                    if (memberList[k + 1] == null) {
                        continue;
                    }
                    if (memberList[k].getLocation().getCounty().equals(memberList[k + 1].getLocation().getCounty())) {
                        if (memberList[k].getLocation().getZipCode() > memberList[k + 1].getLocation().getZipCode()) {
                            Member temp = memberList[k];
                            memberList[k] = memberList[k + 1];
                            memberList[k + 1] = temp;
                        }
                    }
                }
            }
            for (Member member : memberList) {
                if (member != null) {
                    System.out.println(member);
                }
            }
            System.out.println("-end of list-");
            System.out.println();
        }
    }

    /**
     * Prints the Member Data base sorted the expiration date
     */
    public void printByExpirationDate() throws NullPointerException {

        Member[] memberList = mlist;
        int n = memberList.length;
        try {
            if (mlist[0] == null) {
                System.out.println("Member database is empty!");
            } else {
                System.out.println();
                System.out.println("-list of members sorted by membership expiration date-");
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n - i - 1; j++) {
                        // 6 variables, first year month day
                        int firstYear = memberList[j].getExpire().getYear();
                        int firstMonth = memberList[j].getExpire().getMonth();
                        int firstDay = memberList[j].getExpire().getDay();
                        //second year month day
                        if (memberList[j + 1] == null) {
                            break;
                        }
                        int secondYear = memberList[j + 1].getExpire().getYear(); // why the fuck is this going out of bounds
                        int secondMonth = memberList[j + 1].getExpire().getMonth();
                        int secondDay = memberList[j + 1].getExpire().getDay();

                        if (firstYear > secondYear) {
                            Member temp = memberList[j + 1];
                            memberList[j + 1] = memberList[j];
                            memberList[j] = temp;
                        } else if (firstYear == secondYear) {
                            if (firstMonth == secondMonth) {
                                if (firstDay == secondDay) {
                                } else if (firstDay > secondDay) {
                                    Member temp = memberList[j + 1];
                                    memberList[j + 1] = memberList[j];
                                    memberList[j] = temp;
                                }
                            } else if (firstMonth > secondMonth) {
                                Member temp = memberList[j + 1];
                                memberList[j + 1] = memberList[j];
                                memberList[j] = temp;
                            }
                        }
                    }
                }
                for (Member member : memberList) {
                    if (member != null) {
                        System.out.println(member);
                    }
                }
                System.out.println("-end of list-");
                System.out.println();
            }
        } catch (NullPointerException e) {
            System.out.println("Member database is empty!");
        }
    }

    /**
     * Prints the Member Data base sorted by last name and then first name
     */
    public void printByName() {
        Member[] memberList = mlist;
        int n = memberList.length;
        //sort by last name
        if (mlist[0] == null) {
            System.out.println("Member database is empty!");
        } else {
            System.out.println();
            System.out.println("-list of members sorted by last name, and first name-");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (memberList[j + 1] == null || memberList[j] == null) {
                        break;
                    }
                    String firstLastName = memberList[j].getLName().toLowerCase();
                    String secondLastName = memberList[j + 1].getLName().toLowerCase();
                    if (firstLastName.equals(secondLastName)) {
                    } else {
                        //check for which char is greater
                        //if the first<second, just goes to next iteration
                        if (firstLastName.compareTo(secondLastName) > 0) {
                            Member temp = memberList[j];
                            memberList[j] = memberList[j + 1];
                            memberList[j + 1] = temp;
                        }

                    }
                }
            }
            //sort by firstName create two variables to make sure lastNames are equal to eachother or else continue
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (memberList[j + 1] == null || memberList[j] == null) {
                        break;
                    }
                    String firstLastName = memberList[j].getLName().toLowerCase();
                    String secondLastName = memberList[j + 1].getLName().toLowerCase();
                    if (firstLastName.equals(secondLastName)) {
                        String firstPFirstName = memberList[j].getFName().toLowerCase();
                        String secondPFirstName = memberList[j + 1].getFName().toLowerCase();

                        if (firstPFirstName.compareTo(secondPFirstName) > 0) {
                            Member temp = memberList[j];
                            memberList[j] = memberList[j + 1];
                            memberList[j + 1] = temp;
                        }
                    }
                }
            }
            for (Member member : memberList) {
                if (member != null) {
                    System.out.println(member);
                }
            }
            System.out.println("-end of list-");
            System.out.println();
        }
    }

    /**
     * Loads the member list from a .txt file
     *
     * @throws FileNotFoundException if .txt not found.
     */
    public void loadMemberList() throws FileNotFoundException {
        String fname, lname, dobString, expString, locationString;
        File text = new File("src/memberList.txt");
        Scanner memberList = new Scanner(text);
        System.out.println();
        System.out.println("-list of members loaded-");
        while (memberList.hasNextLine()) {
            fname = memberList.next();
            lname = memberList.next();
            dobString = memberList.next();
            expString = memberList.next();
            locationString = memberList.next();

            Date dob = new Date(dobString);
            Date expire = new Date(expString);
            Location location = Location.valueOf(locationString);

            Member member = new Member(fname, lname, dob, expire, location);
            for (int i = 0; i < mlist.length; i++) {
                if (mlist[i] == null) {
                    mlist[i] = member;
                    break;
                }
            }

            if (mlist[mlist.length - 1] != null) {
                grow();
            }

            System.out.println(member);
        }
        System.out.println("-end of list-");
        System.out.println();
    }
}


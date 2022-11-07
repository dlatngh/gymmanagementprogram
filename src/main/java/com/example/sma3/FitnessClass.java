package com.example.sma3;

import java.util.ArrayList;

/**
 * Defines a fitness class the members can check in.
 * Must use the enum class Time.
 *
 * @author David Kim, Sooho Lim
 */

public class FitnessClass {
    /**
     * class name
     */
    private String className;
    /**
     * instructor
     */
    private String instructor;
    /**
     * location
     */
    private Location location;
    /**
     * time
     */
    private Time time;
    /**
     * member list
     */
    private ArrayList<Member> memberList = new ArrayList<>();
    /**
     * guest list
     */
    private ArrayList<Member> guestList = new ArrayList<>();

    /**
     * Default constructor of FitnessClass
     *
     * @param className  time of class
     * @param instructor instructor name
     * @param location   the location of the class
     * @param time       time
     */
    public FitnessClass(String className, String instructor, Location location, Time time) {
        this.className = className;
        this.instructor = instructor;
        this.location = location;
        this.time = time;
    }

    /**
     * Constructor of FitnessClass with List
     *
     * @param className  class name
     * @param instructor instructor name
     * @param location   the location of the class
     * @param memberList list of members checked into class
     * @param guestList  list of guests checked into class
     */
    public FitnessClass(String className, String instructor, Location location, Time time,
                        ArrayList<Member> memberList, ArrayList<Member> guestList) {
        this.className = className;
        this.instructor = instructor;
        this.location = location;
        this.time = time;
        this.memberList = memberList;
        this.guestList = guestList;
    }

    /**
     * Getter for the member list
     *
     * @return arraylist of member list
     */
    public ArrayList<Member> getMemberList() {
        return memberList;
    }

    /**
     * Getter for the guest list
     *
     * @return arraylist of guest list.
     */
    public ArrayList<Member> getGuestList() {
        return guestList;
    }

    /**
     * Getter for the Time enum
     *
     * @return time enum
     */
    public String getClassName() {
        return this.className.toUpperCase();
    }

    /**
     * Getter for the name of the instructor
     *
     * @return the instructors name
     */
    public String getInstructor() {
        return instructor.toUpperCase();
    }

    /**
     * Getter for the Location enum
     *
     * @return location enum
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Getter for the time of class
     *
     * @return the time of class
     */
    public Time getTime() {
        return time;
    }

    /**
     * textual representation of the class
     *
     * @return textual representation of the class
     */
    @Override
    public String toString() {
        return getClassName() + " - " + getInstructor() + ", " + getTime() + ", " + getLocation();
    }
}
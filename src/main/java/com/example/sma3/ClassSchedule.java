package com.example.sma3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Array-based data structure to hold the list of FitnessClasses.
 * Provides methods for managing the list of FitnessClasses
 *
 * @author David Kim, Sooho Lim
 */
public class ClassSchedule {
    private int numClasses = 15;
    private FitnessClass[] classes = new FitnessClass[numClasses];

    /**
     * default constructor. creates a classes object of default cap
     */
    public ClassSchedule() {
    }

    /**
     * Loads fitness class schedule from classSchedule.txt
     *
     * @return list of loaded classes
     */
    public String loadSchedule() throws FileNotFoundException {
        StringBuilder content = new StringBuilder();
        String prefix;
        String suffix;
        String className, instructor, timeString, locationString;
        File text = new File("src/classSchedule.txt");
        Scanner schedule = new Scanner(text);
        prefix = "\n-Fitness classes loaded-";
        content.append(prefix).append("\n");
        while (schedule.hasNextLine()) {
            className = schedule.next();
            instructor = schedule.next();
            timeString = schedule.next();
            locationString = schedule.next();
            timeString = timeString.toUpperCase();
            locationString = locationString.toUpperCase();

            Time time = Time.valueOf(timeString);
            Location location = Location.valueOf(locationString);
            FitnessClass fitnessClass = new FitnessClass(className, instructor, location, time);
            for (int i = 0; i < classes.length; i++) {
                if (classes[i] == null) {
                    classes[i] = fitnessClass;
                    break;
                }
            }
            content.append(fitnessClass).append("\n");
        }
        suffix = "-end of class list.";
        content.append(suffix).append("\n");
        return content.toString();
    }

    /**
     * Finds the class from the following
     *
     * @param className  of the class
     * @param instructor teaching
     * @param location   of the class
     * @return object Fitnessclass if found, null otherwise.
     */
    public FitnessClass findClass(String className, String instructor, Location location) {
        FitnessClass targetClass = null;
        for (FitnessClass fitnessClass : classes) {
            if (fitnessClass != null) {
                if (fitnessClass.getClassName().equalsIgnoreCase(className) && fitnessClass.getInstructor().equalsIgnoreCase(instructor) && fitnessClass.getLocation().equals(location)) {
                    targetClass = fitnessClass;
                }
            }
        }
        return targetClass;
    }

    public boolean canCheckInElseWhere(Member member, String location) {
        boolean isFamily = member instanceof Family;
        boolean isPremium = member instanceof Premium;
        if (isFamily || isPremium) {
            return true;
        }
        return member.getLocation().toString().equals(location.toUpperCase());
    }

    /**
     * Checks if instructor is valid
     *
     * @param instructor to check
     * @return true if valid, false otherwise.
     */
    public boolean isValidInstructor(String instructor) {
        for (FitnessClass fitnessClass : classes) {
            if (fitnessClass.getInstructor().equalsIgnoreCase(instructor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if classname is a valid class
     *
     * @param className of the class
     * @return true if it is a valid class, false otherwise.
     */
    public boolean isValidClass(String className) {
        for (FitnessClass fitnessClass : classes) {
            if (fitnessClass.getClassName().equalsIgnoreCase(className)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the class offered is at location
     *
     * @param location   of the class
     * @param instructor teaching
     * @param className  of the class
     * @return true if offered, false otherwise.
     */
    public boolean isAtLocation(String location, String instructor, String className) {
        for (FitnessClass fitnessClass : classes) {
            if (fitnessClass.getLocation().toString().equalsIgnoreCase(location) && fitnessClass.getClassName().equalsIgnoreCase(className) && fitnessClass.getInstructor().equalsIgnoreCase(instructor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if fitnessclass that the member is trying to check into has a time conflict
     *
     * @param fitnessClass the member is trying to check in
     * @param member       that's trying to check in
     * @return true if conflicted, false otherwise.
     */
    public boolean isConflict(FitnessClass fitnessClass, Member member) {
        for (FitnessClass fitnessClasses : classes) {
            if ((fitnessClass.getLocation().equals(fitnessClasses.getLocation()) && fitnessClasses.getMemberList().contains(member)) || (fitnessClass.getTime().equals(fitnessClasses.getTime()) && fitnessClasses.getMemberList().contains(member))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks a member into the class
     *
     * @param fitnessClass of the class to check into
     * @param member       to check in
     * @return true if successfully checked in, false otherwise
     */
    public boolean checkIn(FitnessClass fitnessClass, Member member) {
        MemberDatabase memberDatabase = new MemberDatabase();
        if (memberDatabase.findMember(member.getFName(), member.getLName(), member.getDob()) != null) {
            return false;
        } else {
            for (FitnessClass fitnessClasses : classes) {
                if (fitnessClass.equals(fitnessClasses)) {
                    if (!find(fitnessClass, member)) {
                        fitnessClass.getMemberList().add(member);
                        break;
                    } else return false;
                }
            }
        }
        return true;
    }

    /**
     * Finds a member in the member array list of a fitnessclass
     *
     * @param fitnessClass you're searching
     * @param member       you're looking for
     * @return true if found, false otherwise
     */
    public boolean find(FitnessClass fitnessClass, Member member) {
        return fitnessClass.getMemberList().contains(member);
    }

    /**
     * Checks a member out of the class
     *
     * @param fitnessClass of the class to check out of
     * @param member       to check out
     * @return true if successfully checked out, false otherwise.
     */
    public boolean checkOut(FitnessClass fitnessClass, Member member) {
        MemberDatabase memberDatabase = new MemberDatabase();
        if (memberDatabase.findMember(member.getFName(), member.getLName(), member.getDob()) != null) {
            return false;
        } else {
            for (FitnessClass fitnessClasses : classes) {
                if (fitnessClass.equals(fitnessClasses)) {
                    if (fitnessClass.getMemberList().contains(member)) {
                        fitnessClass.getMemberList().remove(member);
                        break;
                    } else return false;
                }
            }
        }
        return true;
    }

    /**
     * Prints the Fitness Class Schedule
     */
    public String print() {
        StringBuilder content = new StringBuilder();
        String error = "Fitness class schedule is empty.\n";
        String prefix = "\n-Fitness classes-\n";
        if (classes[0] == null) return error;
        else {
            content.append(prefix);
            for (FitnessClass fitnessClass : classes) {
                if (fitnessClass != null) {
                    content.append(fitnessClass).append("\n");
                    if (!fitnessClass.getMemberList().isEmpty()) {
                        content.append("- Participants -\n");
                        for (int i = 0; i < fitnessClass.getMemberList().size(); i++)
                            content.append("   ").append(fitnessClass.getMemberList().get(i)).append("\n");
                    }
                    if (!fitnessClass.getGuestList().isEmpty()) {
                        content.append("- Guests -\n");
                        for (int i = 0; i < fitnessClass.getGuestList().size(); i++)
                            content.append("   ").append(fitnessClass.getGuestList().get(i)).append("\n");
                    }
                }
            }
            content.append("-end of class list.\n");
        }
        return content.toString();
    }

    /**
     * Checking in guest
     *
     * @param fitnessClass to check guest into
     * @param member's     guest
     */
    public boolean checkGuest(FitnessClass fitnessClass, Member member) {
        if (member instanceof Premium) {
            if (((Premium) member).getGuestPasses() > 0) {
                fitnessClass.getGuestList().add(member);
                return true;
            }
        } else if (member instanceof Family) {
            if (((Family) member).getGuestPasses() > 0) {
                fitnessClass.getGuestList().add(member);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks guest out
     *
     * @param fitnessClass to check guest out of
     * @param member's     guest
     */
    public boolean checkGuestOut(FitnessClass fitnessClass, Member member) {
        fitnessClass.getGuestList().remove(member);
        return true;
    }
}



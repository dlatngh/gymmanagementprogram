package com.example.sma3;

/**
 * Class for Family that extends Member class
 * and includes specific data and operations to a family membership;
 * <p>
 * One time fee is $29.99
 * Fee schedule is $59.99 per month
 * Fitness class benefits are: any location, 1 family guest pass at membership location only
 *
 * @author David Kim, Sooho Lim
 */
public class Family extends Member {
    /**
     * membership fee
     */
    private double membershipFee;
    /**
     * monthly fee
     */
    private double monthlyFee;
    /**
     * guest passes
     */
    private int guestPasses;
    /**
     * defines the family rate
     */
    private static final double FAMILY_RATE = 59.99;
    /**
     * defines the montly rate
     */
    private static final int QUARTERLY = 3;
    /**
     * defines guest passes
     */
    private static final int GUESTPASS = 1;


    /**
     * Default constructor of Member
     *
     * @param fname         first name
     * @param lname         last name
     * @param dob           date of birth
     * @param expire        membership expiration date
     * @param location      location of gym
     * @param guestPasses   guest passes
     * @param monthlyFee    monthly fee
     * @param membershipFee membership fee
     */
    public Family(String fname, String lname, Date dob, Date expire, Location location,
                  double membershipFee, double monthlyFee, int guestPasses) {
        super(fname, lname, dob, expire, location);
        this.monthlyFee = monthlyFee;
        this.membershipFee = membershipFee;
        this.guestPasses = GUESTPASS;
    }

    /**
     * Method returns a textual representation of a member with a family membership.
     * format as follows:
     * April March, DOB: 3/31/1990, Membership expires 6/30/2023, Location: PISCATAWAY, 08854, MIDDLESEX, (Family) guest-pass remaining: 1"
     *
     * @return textual representation of a member with family membership
     */
    @Override
    public String toString() {
        return super.toString() + ", (Family) guest-passes remaining: " + guestPasses;
    }

    /**
     * Calculates the family membership fee.
     * One time fee of $29.99 plus $59.99 per month paid quarterly.
     *
     * @return family membership fee.
     */
    @Override
    public double membershipFee() {
        return super.getMemberFee() + (FAMILY_RATE * QUARTERLY);
    }

    /**
     * Method returns a textual representation of a default member
     *
     * @return default member textual representation
     */
    public String getMemberString() {
        return super.toString();
    }

    /**
     * Gets the monthly fee for the family membership.
     *
     * @return Family Rate
     */
    public double getMonthlyFee() {
        return FAMILY_RATE;
    }


    /**
     * Gets the number of guest passes for the family membership.
     *
     * @return guest passes
     */
    public int getGuestPasses() {
        return guestPasses;
    }

    /**
     * uses up a guest pass
     */
    public void useGuestPass() {
        this.guestPasses = 0;
    }

    /**
     * regain a guest pass
     */
    public void addGuestPass() {
        this.guestPasses = 1;
    }

}

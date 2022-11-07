package com.example.sma3;

/**
 * Class for Premiums that extends Family class
 * and includes specific data and operations to a premium membership.
 * <p>
 * One time fee is waived.
 * Fee schedule is 1 month free of family rate, pay annually.
 * Fitness class benefits are: same as family, 3 family guest pass at membership location only
 *
 * @author David Kim, Sooho Lim
 */
public class Premium extends Family {
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
     * defines billing cycle
     */
    private static final int ANNUAL = 12;
    /**
     * defines guest passes
     */
    private static final int GUESTPASSES = 3;

    /**
     * Default constructor of Member
     *
     * @param fname         first name
     * @param lname         last name
     * @param dob           date of birth
     * @param expire        membership expiration date
     * @param location      location of gym
     * @param membershipFee membership fee
     * @param monthlyFee    monthly fee
     * @param guestPasses   guest passes
     */
    public Premium(String fname, String lname, Date dob, Date expire, Location location,
                   double membershipFee, double monthlyFee, int guestPasses) {
        super(fname, lname, dob, expire, location, membershipFee, monthlyFee, guestPasses);
        this.monthlyFee = monthlyFee;
        this.membershipFee = membershipFee;
        this.guestPasses = GUESTPASSES;

    }


    /**
     * Calculates the premium membership fee.
     * One month free of Family membership rate, paid annually.
     *
     * @return premium membership fee.
     */
    @Override
    public double membershipFee() {
        return (super.getMonthlyFee() * ANNUAL) - super.getMonthlyFee();
    }

    /**
     * Gets the number of guest passes in the premium membership fee.
     *
     * @return number of guest passes
     */
    public int getGuestPasses() {
        return guestPasses;
    }

    /**
     * uses up a guest pass
     */
    public void useGuestPasses() {
        this.guestPasses = this.guestPasses - 1;
    }

    /**
     * gain a guest pass
     */
    public void addGuestPass() {
        this.guestPasses = this.guestPasses + 1;
    }

    /**
     * Method returns a textual representation of a member with the premium membership.
     * format as follows:
     * April March, DOB: 3/31/1990, Membership expires 6/30/2023, Location: PISCATAWAY, 08854, MIDDLESEX, (Premium) guest-pass remaining: 3"
     *
     * @return textual representation of a member with premium membership
     */
    @Override
    public String toString() {
        return super.getMemberString() + ", (Premium) guest-passes remaining: " + guestPasses;
    }

    /**
     * getter for membership fee
     *
     * @return membership fee
     */
    public double getMembershipFee() {
        return membershipFee;
    }

    /**
     * getter for monthly fee
     *
     * @return monthly fee
     */
    @Override
    public double getMonthlyFee() {
        return monthlyFee;
    }
}

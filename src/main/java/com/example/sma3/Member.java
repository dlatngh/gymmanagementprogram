package com.example.sma3;

/**
 * Creates the object Member.
 * Defines standard Membership and common instance variables
 * and instance methods of Family and Premium.
 *
 * @author David Kim, Sooho Lim
 */
public class Member implements Comparable<Member> {
    /**
     * first name
     */
    private String fname;
    /**
     * last name
     */
    private String lname;
    /**
     * date of birth
     */
    private Date dob;
    /**
     * expiration date
     */
    private Date expire;
    /**
     * location
     */
    private Location location;
    /**
     * defines membership fee
     */
    private static final double MEMBER_FEE = 29.99;
    /**
     * defines monthly fee
     */
    private static final double MONTHLY_FEE = 39.99;
    /**
     * defines the cycle
     */
    private static final int QUARTERLY = 3;

    /**
     * Default constructor of Member
     *
     * @param fname    first name
     * @param lname    last name
     * @param dob      date of birth
     * @param expire   membership expiration date
     * @param location location of gym
     */
    public Member(String fname, String lname, Date dob, Date expire, Location location) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.expire = expire;
        this.location = location;
    }

    /**
     * getter for the first name
     *
     * @return first name
     */
    public String getFName() {
        return this.fname;
    }

    /**
     * getter for the last name
     *
     * @return last name
     */
    public String getLName() {
        return this.lname;
    }

    /**
     * getter for the name
     *
     * @return full name to string
     */
    public String getName() {
        return this.fname + " " + this.lname;
    }

    /**
     * getter for the dob
     *
     * @return dob obj
     */
    public Date getDob() {
        return this.dob;
    }

    /**
     * getter for the expiration date
     *
     * @return expiration date obj
     */
    public Date getExpire() {
        return this.expire;
    }

    /**
     * getter for the location enum
     *
     * @return location enum
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * getter for the member object
     *
     * @return member object
     */
    public Member getMember() {
        return new Member(this.fname, this.lname, this.dob, this.expire, this.location);
    }

    /**
     * method returns a textual representation of a member.
     * format as follows:
     * April March, DOB: 3/31/1990, Membership expires 6/30/2023, Location: PISCATAWAY, 08854, MIDDLESEX
     *
     * @return textual representation of a member
     */
    @Override
    public String toString() {
        String dobTemp = this.dob.dateToString(this.dob);
        String expireTemp = this.expire.dateToString(this.expire);
        if (!this.expire.isFuture()) {
            return this.getFName() + " " + this.getLName() + ", DOB: " + dobTemp +
                    ", Membership expired " + expireTemp + ", Location: " +
                    this.getLocation() + ", 0" + this.getLocation().getZipCode() +
                    ", " + this.getLocation().getCounty();
        }
        return this.getFName() + " " + this.getLName() + ", DOB: " + dobTemp +
                ", Membership expires " + expireTemp + ", Location: " +
                this.getLocation() + ", 0" + this.getLocation().getZipCode() +
                ", " + this.getLocation().getCounty();
    }

    /**
     * returns a boolean when the first names, last names, and DOB are equal
     *
     * @param obj of the people to compare
     * @return true if equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Member member) {
            //casting
            return member.fname.equalsIgnoreCase(this.fname)
                    && member.dob.equals(this.dob)
                    && member.lname.equalsIgnoreCase(this.lname);
        }
        return false;
    }

    /**
     * used when sorting by names
     *
     * @param member the object to be compared.
     * @return 0 if strings are equal, negative number if string is lexicographically less
     * than other string, positive number if the string is lexicographically greater than
     * the other string.
     */
    @Override
    public int compareTo(Member member) {
        int lastCompare = this.lname.compareTo(member.lname);
        return lastCompare != 0 ? lastCompare : this.fname.compareTo(member.fname);
    }

    /**
     * Calculates regular membership fee.
     *
     * @return membership fee
     */
    public double membershipFee() {
        return MEMBER_FEE + (MONTHLY_FEE * QUARTERLY);
    }

    /**
     * Gets the default one time membership fee.
     *
     * @return one time membership fee.
     */
    public double getMemberFee() {
        return MEMBER_FEE;
    }


    /**
     * testbed for the method compareTo
     *
     * @param args command line inputs
     */
    public static void main(String[] args) {
        Member member1 = new Member("John", "Doe", new Date("1/20/2003"), new Date("3/30/2021"), Location.BRIDGEWATER);
        Member member2 = new Member("Francis", "Peel", new Date("5/1/1996"), new Date("3/30/2023"), Location.FRANKLIN);
        Member member3 = new Member("Krib", "Stop", new Date("5/1/1996"), new Date("3/30/2023"), Location.SOMERVILLE);
        Member member4 = new Member("Gel", "Fens", new Date("5/1/1996"), new Date("3/30/2023"), Location.EDISON);
        Member member5 = new Member("Sooho", "Lim", new Date("10/04/2000"), new Date("10/04/2023"), Location.PISCATAWAY);
        Member member6 = new Member("Sydney", "Landers", new Date("7/19/2002"), new Date("7/19/2023"), Location.FRANKLIN);
        Member member7 = new Member("David", "Kim", new Date("6/3/2000"), new Date("6/3/2023"), Location.EDISON);
        Member member8 = new Member("David", "Kim", new Date("2/5/1999"), new Date("7/1/2024"), Location.SOMERVILLE);
        Member member9 = new Member("Hweeho", "Lim", new Date("4/25/2003"), new Date("4/25/2025"), Location.BRIDGEWATER);
        Member member10 = new Member("Okran", "Kim", new Date("1/29/1974"), new Date("1/29/2023"), Location.EDISON);

        System.out.println(member1.compareTo(member2));     //  test case 1
        System.out.println(member3.compareTo(member4));     //  test case 2
        System.out.println(member5.compareTo(member6));     //  test case 3
        System.out.println(member7.compareTo(member8));     //  test case 4
        System.out.println(member9.compareTo(member10));    //  test case 5
        System.out.println(member2.compareTo(member10));    //  test case 6
        System.out.println(member5.compareTo(member9));     //  test case 7
        System.out.println(member7.compareTo(member10));    //  test case 8
        System.out.println(member10.compareTo(member7));    //  test case 9
        System.out.println(member4.compareTo(member10));    //  test case 10
    }
}


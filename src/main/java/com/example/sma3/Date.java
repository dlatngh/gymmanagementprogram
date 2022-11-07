package com.example.sma3;

import java.util.Calendar;


/**
 * Creates the object Date.
 *
 * @author David Kim, Sooho Lim
 */

public class Date implements Comparable<Date> {
    public static final int JAN = 31;
    public static final int FEB = 28;
    public static final int LEAP_FEB = 29;
    public static final int MAR = 31;
    public static final int APRIL = 30;
    public static final int MAY = 31;
    public static final int JUN = 30;
    public static final int JUL = 31;
    public static final int AUG = 31;
    public static final int SEPT = 30;
    public static final int OCT = 31;
    public static final int NOV = 30;
    public static final int DEC = 31;
    public static final int MINAGE = 18;
    public static final int MAXMONTHS = 12;
    public static final int MINMONTHS = 1;
    public static final int MAXYEAR = 2023;
    public static final int MINYEAR = 1977;
    public static final int LESS_THAN = -1;
    private int year;
    private int month;
    private int day;

    /**
     * Create an object with today’s date (see Calendar class).
     */
    public Date() {
        Calendar calendar = Calendar.getInstance();
        int currYear = calendar.get(Calendar.YEAR);
        int currDay = calendar.get(Calendar.DATE);
        int currMonth = calendar.get(Calendar.MONTH) + 1;

        String today = currMonth + "/" + currDay + "/" + currYear;
        Date currDate = new Date(today);
    }

    /**
     * Take in date as “mm/dd/yyyy” and create a Date object.
     *
     * @param date to take in.
     */
    public Date(String date) {
        String[] dArr = date.split("/");
        for (int i = 0; i < dArr.length; i++) {
            this.month = Integer.parseInt(dArr[0]);
            this.day = Integer.parseInt(dArr[1]);
            this.year = Integer.parseInt(dArr[2]);
        }
    }

    /**
     * Reverse method where string is created to replace this.dob in toString method
     *
     * @param date to turn into string
     * @return the date in string
     */
    public String dateToString(Date date) {
        return date.getMonth() + "/" + date.getDay() + "/" + date.getYear();
    }

    /**
     * getter for month
     *
     * @return month
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * getter for day
     *
     * @return day
     */
    public int getDay() {
        return this.day;
    }

    /**
     * getter for year
     *
     * @return year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Compare Date objects
     *
     * @param obj date parameters
     * @return true if same date, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date Date) {
            return Date.month == month
                    && Date.year == year
                    && Date.day == day;
        }
        return false;
    }

    /**
     * Getter for current year
     *
     * @return current year
     */
    public static int getCurrYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Getter for current month
     *
     * @return current month
     */
    public static int getCurrMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * Getter for current day
     *
     * @return current day
     */
    public static int getCurrDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }


    /**
     * Used when sorting by Expiration Date
     *
     * @param date to compare
     * @return 0 if dates are equal,
     * less than 0 if date is lexicographically less than other date,
     * greater than 0 if the date is lexicographically greater than the other date.
     */
    @Override
    public int compareTo(Date date) {
        if (this.year != date.year) {
            return this.year > date.year ? 1 : LESS_THAN;
        } else if (this.month != date.month) {
            return this.month > date.month ? 1 : LESS_THAN;
        } else if (this.day != date.day) {
            return this.day > date.day ? 1 : LESS_THAN;
        } else {
            return 0;
        }
    }

    /**
     * Checks if year inputted is a leap year.
     * To determine days in FEB.
     *
     * @param year to check.
     * @return true if leap year, false otherwise.
     */
    private boolean isLeapYear(int year) {
        int leapYearTest1 = 4;
        int leapYearTest2 = 100;
        int leapYearTest3 = 400;
        if (year % leapYearTest1 == 0 && year % leapYearTest2 == 0) {
            return year % leapYearTest3 == 0;
        } else {
            return false;
        }
    }

    /**
     * Checks if a date is a valid calendar date.
     * Months 1, 3, 5, 7, 8, 10, 12 has 31 days.
     * Months 4, 6, 9, 11 has 30 days.
     * Month 2 has 28 days in a non-leap year and 29 in a leap year.
     *
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        int indexFeb = 1;
        if (this.year >= MINYEAR && this.year <= MAXYEAR && this.month >= MINMONTHS && this.month <= MAXMONTHS) {

            int[] daysInMonth = new int[]{JAN, FEB, MAR, APRIL, MAY, JUN, JUL, AUG, SEPT, OCT, NOV, DEC};

            if (this.isLeapYear(this.year)) {
                daysInMonth[indexFeb] = LEAP_FEB;
            }
            return this.day > 0 && this.day <= daysInMonth[this.month - 1];
        } else {
            return false;
        }
    }

    /**
     * checks if date is today's date
     *
     * @return true if today, false otherwise
     */
    public boolean isToday() {
        return getYear() == getCurrYear() && getMonth() == getCurrMonth() && getDay() == getCurrDay();
    }

    /**
     * Checks if date is in the future
     *
     * @return true if future, false otherwise.
     */
    public boolean isFuture() {
        if (getCurrYear() - getYear() < 0) {
            return true;
        } else if (getCurrYear() - getYear() == 0) {
            if (getCurrMonth() - getMonth() < 0) {
                return true;
            } else if (getCurrMonth() - getMonth() == 0) {
                return getCurrDay() - getDay() < 0;
            }
        }
        return false;
    }

    /**
     * Checks if member is over the age of 18 or not.
     *
     * @return true if under 18, false otherwise.
     */
    public boolean isMinor() {
        if (getCurrYear() - getYear() < MINAGE) {
            return true;
        } else if (getCurrYear() - getYear() == MINAGE) {
            if (getCurrMonth() - getMonth() < 0) {
                return true;
            } else if (getCurrMonth() - getMonth() == 0) {
                return getCurrDay() - getDay() < 0;
            }
        }
        return false;
    }
}
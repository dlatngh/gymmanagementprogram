package com.example.sma3;

/**
 * Defines the time of a fitness class.
 *
 * @author David Kim, Sooho Lim
 */
public enum Time {
    MORNING("9", "30"),
    AFTERNOON("14", "00"),
    EVENING("18", "30");
    private final String hour;
    private final String minute;

    /**
     * default constructor for the Time enum.
     *
     * @param hour   the hour.
     * @param minute the minute.
     */
    Time(String hour, String minute) {

        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Getter for hour value.
     *
     * @return hour value.
     */
    public String getHour() {
        return hour;
    }

    /**
     * Getter for minute value.
     *
     * @return minute value.
     */
    public String getMinute() {
        return minute;
    }

    /**
     * Enum to string.
     *
     * @return the time in string.
     */
    @Override
    public String toString() {
        return getHour() + ":" + getMinute();
    }
}
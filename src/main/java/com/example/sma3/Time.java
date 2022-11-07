package com.example.sma3;

/**
 * Defines the time of a fitness class.
 *
 * @author David Kim, Sooho Lim
 */
public enum Time {
    /**
     * defines the morning
     */
    MORNING("9", "30"),
    /**
     * defines the afternoon
     */
    AFTERNOON("14", "00"),
    /**
     * defines the evening
     */
    EVENING("18", "30");
    /**
     * defines hour variable
     */
    private final String hour;
    /**
     * defines minute variable
     */
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
package com.example.sma3;

/**
 * Defines the 5 gym locations.
 * Refer to Lecture Note #2.
 *
 * @author David Kim, Sooho Lim
 */
public enum Location {
    /**
     * defines the bridgewater location
     */
    BRIDGEWATER(8807, "SOMERSET"),
    /**
     * defines the edison location
     */
    EDISON(8837, "MIDDLESEX"),
    /**
     * defines the franklin location
     */
    FRANKLIN(8873, "SOMERSET"),
    /**
     * defines the piscataway location
     */
    PISCATAWAY(8854, "MIDDLESEX"),
    /**
     * defines the somerville location
     */
    SOMERVILLE(8876, "SOMERSET");
    /**
     * zipcodes
     */
    private final int zipCode;
    /**
     * county names
     */
    private final String county;

    /**
     * Constructor for the Location enum
     *
     * @param zipCode of the location
     * @param county  of the location
     */
    Location(int zipCode, String county) {
        this.zipCode = zipCode;
        this.county = county;
    }

    /**
     * Getter for the zipcode
     *
     * @return the zipcode
     */
    public int getZipCode() {
        return zipCode;
    }

    /**
     * Getter for the county name
     *
     * @return county name
     */
    public String getCounty() {
        return county;
    }

    /**
     * Checks if string is a valid location enum
     *
     * @param locationToCheck string to check
     * @return true if valid, false otherwise
     */
    public static boolean isValidLoc(String locationToCheck) {
        Location[] locList = Location.values();
        for (Location loc : locList) {
            if (loc.name().equalsIgnoreCase(locationToCheck)) {
                return true;
            }
        }
        return false;
    }
}
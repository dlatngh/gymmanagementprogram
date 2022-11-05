package com.example.sma3;

import java.io.FileNotFoundException;

/**
 * Driver class to run Project 2.
 *
 * @author David Kim, Sooho Lim
 */
public class RunProject2 {
    /**
     * Calls the run() method in the GymManager class.
     */
    public RunProject2() {
    }

    /**
     * Driver
     * @param args std in
     */
    public static void main(String[] args) throws FileNotFoundException {
        new GymManager().run();
    }
}
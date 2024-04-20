package com.example.javaproject;

import java.util.ArrayList;
import java.util.List;

public class MySingleton {
    // private static instance variable to hold the singleton instance
    private static volatile MySingleton INSTANCE = null;

    private List<String> myArray = new ArrayList<>();

    // private constructor to prevent instantiation of the class
    private MySingleton() {}

    // public static method to retrieve the singleton instance
    public static MySingleton getInstance() {
        // Check if the instance is already created
        if(INSTANCE == null) {
            // synchronize the block to ensure only one thread can execute at a time
            synchronized (MySingleton.class) {
                // check again if the instance is already created
                if (INSTANCE == null) {
                    // create the singleton instance
                    INSTANCE = new MySingleton();
                }
            }
        }
        // return the singleton instance
        return INSTANCE;
    }

    public List<String> getMyArray() {
        return myArray;
    }

    public void setMyArray(List<String> myArray) {
        this.myArray = myArray;
    }
}


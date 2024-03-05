package com.example.javaproject.network;

import com.example.javaproject.MyService;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class Api {
    // private static instance variable to hold the singleton instance
    private static volatile Api INSTANCE = null;

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breeds/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build();

    // private constructor to prevent instantiation of the class
    private Api() {}

    // public static method to retrieve the singleton instance
    public static Api getInstance() {
        // Check if the instance is already created
        if(INSTANCE == null) {
            // synchronize the block to ensure only one thread can execute at a time
            synchronized (Api.class) {
                // check again if the instance is already created
                if (INSTANCE == null) {
                    // create the singleton instance
                    INSTANCE = new Api();
                }
            }
        }
        // return the singleton instance
        return INSTANCE;
    }

    public MyService getService() {
        return retrofit.create(MyService.class);
    }
}
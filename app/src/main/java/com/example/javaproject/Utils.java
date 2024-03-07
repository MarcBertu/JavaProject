package com.example.javaproject;

import android.util.Log;

public class Utils {
    public static Long executeLongActionDuring7seconds(){

        Log.e("TAG", "Long action is starting...");
        Long i = 0L;
        while (i <  100) {
            Log.i("Numer_iteraiton", String.valueOf(i));
        }
        Log.e("TAG", "Long action is finished !");

        return 0L;
    }
}

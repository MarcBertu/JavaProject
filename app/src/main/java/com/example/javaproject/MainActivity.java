package com.example.javaproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.javaproject.model.ResponseModel;
import com.example.javaproject.network.Api;
import com.example.javaproject.network.BasicTask;
import com.example.javaproject.network.RetrofitTask;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RetrofitTask.RetrofitInterface, BasicTask.BasicInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(c -> {
            RetrofitTask task = new RetrofitTask(this);
            task.execute();


            BasicTask basicTask = new BasicTask(this);
            basicTask.execute();
        });
    }

    @Override
    public void onSuccesRetrofit(int code) {

    }

    @Override
    public void onFailedRetrofit(int code) {

    }
}
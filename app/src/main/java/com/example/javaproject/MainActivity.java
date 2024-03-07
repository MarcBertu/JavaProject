package com.example.javaproject;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MyAsyncTask.MyInterface {

    ProgressBar progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.progressBar = findViewById(R.id.progress_bar);

        new MyAsyncTask(this, progressBar).execute();
    }

    @Override
    public void doit() {

    }
}
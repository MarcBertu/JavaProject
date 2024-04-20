package com.example.javaproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.javaproject.databinding.ActivityActivityBinding;

public class ActivityA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityActivityBinding binding = ActivityActivityBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        ActivityAAdapter adapter = new ActivityAAdapter(getSupportFragmentManager(), getLifecycle());

        binding.viewpager.setAdapter(adapter);
    }
}
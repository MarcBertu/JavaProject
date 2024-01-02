package com.example.javaproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaproject.databinding.ActivityMainBinding;
import com.example.javaproject.room.model.User;
import com.example.javaproject.room.repository.UserRepository;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_KEY = "MyPreferences";
    private static final int PRIVATE_MODE = Context.MODE_PRIVATE;

    /**
     *  This branch as for goal to create an authentication app that first
     *  register the user by a username and a password store in an sqlLite database
     *  And after all configuration is clear the user can also choice to authenticate via biometrical system (if it possible)
     *
     *  The third we'll be to use the last feature of google account : Google key print
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        TextInputLayout loginInput = binding.loginField;
        TextInputLayout passwordInput = binding.passwordField;


        binding.registerButton.setOnClickListener(c -> {
            UserRepository userRepository = new UserRepository(getApplication());

            String username = loginInput.getEditText().getText().toString();
            String password = passwordInput.getEditText().getText().toString();

            User user = new User(username, password);

            userRepository.insert(user);

            Log.i("ROOM", userRepository.getAll().toString() );

            Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();

        });

        binding.loginButton.setOnClickListener(c -> {
            UserRepository userRepository = new UserRepository(getApplication());

            String username = loginInput.getEditText().getText().toString();
            String password = passwordInput.getEditText().getText().toString();

            User user = userRepository.getUser(username);

            if (user != null && user.getPassword().equals(password)) {
                Toast.makeText(this, "Account found", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Account not exist", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
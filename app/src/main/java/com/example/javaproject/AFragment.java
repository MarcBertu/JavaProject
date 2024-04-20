package com.example.javaproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.javaproject.databinding.FragmentABinding;

import java.util.ArrayList;
import java.util.List;

public class AFragment extends Fragment {

    private boolean isRestored = false;

    private List<String> stringList = new ArrayList<>();

    private TextView textView = null;

    public AFragment() {
        // Required empty public constructor
    }

    public static AFragment newInstance(String param1, String param2) {
        AFragment fragment = new AFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            return;
        }

        this.isRestored = (boolean) savedInstanceState.get("isRestored");

        if(isRestored) {
            this.stringList = MySingleton.getInstance().getMyArray();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentABinding binding = FragmentABinding.inflate(inflater, container, false);
        this.textView = binding.textviewFragmentA;

        binding.confirmButton.setOnClickListener(c -> {
            stringList.add("Salut");
            textView.setText("Test");
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!stringList.isEmpty()) {
            textView.setText(stringList.get(0));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("isRestored", true);

        MySingleton.getInstance().setMyArray(stringList);
    }
}
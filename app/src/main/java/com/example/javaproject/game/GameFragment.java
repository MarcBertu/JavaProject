package com.example.javaproject.game;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaproject.databinding.FragmentGameBinding;
import com.example.javaproject.utils.GameViewModel;

public class GameFragment extends Fragment {

    private GameViewModel viewModel;

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentActivity activity = getActivity();
        if (activity != null) {
            this.viewModel = new ViewModelProvider(activity).get(GameViewModel.class);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentGameBinding binding = FragmentGameBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        RecyclerView recyclerView = binding.gamePadRecyclerview;

        recyclerView.setAdapter(new CaseAdapter(viewModel));

        binding.setViewModel(this.viewModel);

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Game is finish")
                .setMessage("Do you want to restart the game ?")
                .setPositiveButton("Reset", (dialogInterface, i) -> viewModel.reset())
                .setNegativeButton("Cancel" , (dialogInterface, i) -> {})
                .create();

        this.viewModel.whoIsWinner.observe(getViewLifecycleOwner(), winner -> {
            switch (winner) {
                case 1:
                    Toast.makeText(getContext(), "The winner is X", Toast.LENGTH_LONG).show();
                    dialog.show();
                    break;
                case 2:
                    Toast.makeText(getContext(), "The winner is O", Toast.LENGTH_LONG).show();
                    dialog.show();
                    break;
                case 0:
                default:
                    break;
            }
        });

        return binding.getRoot();
    }
}
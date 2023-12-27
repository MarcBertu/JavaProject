package com.example.javaproject.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.javaproject.R;
import com.example.javaproject.utils.GameViewModel;

public class PlayerSetupFragment extends Fragment {

    private GameViewModel viewModel;
    private NavController navController;

    public PlayerSetupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentActivity activity = getActivity();
        if (activity != null) {
            this.viewModel = new ViewModelProvider(activity).get(GameViewModel.class);
        }

        this.navController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player_setup, container, false);

        // Binding

        EditText firstPlayerName = view.findViewById(R.id.first_player_input);
        EditText secondPlayerName = view.findViewById(R.id.second_player_input);

        // Setup

        this.viewModel.init();

        // Add listener on start button

        Button startGameButton = view.findViewById(R.id.start_game_button);
        startGameButton.setOnClickListener( l -> {

            // Editable to String
            String firstPlayerNameString = firstPlayerName.getText().toString();
            String secondPlayerNameString = secondPlayerName.getText().toString();

            if(!checkPlayersNameNotEmptyOrBlank(firstPlayerNameString, secondPlayerNameString)) {
                this.viewModel.setupPlayersNames(firstPlayerNameString, secondPlayerNameString);

                this.navController.navigate(R.id.action_playerSetupFragment_to_gameFragment);
            }
        });

        return view;
    }

    private boolean checkPlayersNameNotEmptyOrBlank(String playerOne, String playerTwo) {
        return playerOne.trim().isEmpty() || playerTwo.trim().isEmpty();
    }
}
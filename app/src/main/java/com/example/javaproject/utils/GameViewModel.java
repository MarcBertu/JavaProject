package com.example.javaproject.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javaproject.game.Case;

import java.util.Arrays;
import java.util.List;

public class GameViewModel extends ViewModel {

    private final MutableLiveData<String> mutableFirstPlayer = new MutableLiveData<>("");
    public LiveData<String > firstPlayer = mutableFirstPlayer;

    private final MutableLiveData<String> mutableSecondPlayer = new MutableLiveData<>("");
    public LiveData<String> secondPlayer = mutableSecondPlayer;

    private final MutableLiveData<List<Case>> mutableCasesList = new MutableLiveData<>();
    public LiveData<List<Case>> casesList = mutableCasesList;

    private final MutableLiveData<Boolean> mutableIsFirstPlayerPlaying = new MutableLiveData<>(true);
    public LiveData<Boolean> isFirstPlayerPlaying = mutableIsFirstPlayerPlaying;

    private final MutableLiveData<Integer> mutableWhoIsWinner = new MutableLiveData<>(0);

    public LiveData<Integer> whoIsWinner = mutableWhoIsWinner;


    public void init() {

        // Set players
        this.mutableFirstPlayer.postValue("");
        this.mutableSecondPlayer.postValue("");

        this.reset();
    }

    public void reset() {
        // Reset winner
        this.mutableWhoIsWinner.postValue(0);

        // Reset the grid
        List<Case> cases = Arrays.asList(
                new Case(), new Case(), new Case(),
                new Case(), new Case(), new Case(),
                new Case(), new Case(), new Case()
        );
        this.mutableCasesList.postValue(cases);
    }

    public void setupPlayersNames(String playerOne, String playerTwo) {
        mutableFirstPlayer.postValue(playerOne);
        mutableSecondPlayer.postValue(playerTwo);
    }

    public void childrenUpdatePosition(int position) {
        List<Case> tempList = mutableCasesList.getValue();
        if (tempList != null) {

            boolean isFirstPlayer = Boolean.TRUE.equals(mutableIsFirstPlayerPlaying.getValue());

            Case.CaseState state = Boolean.TRUE.equals(isFirstPlayer) ? Case.CaseState.X : Case.CaseState.O;
            tempList.get(position).setState( state );
            mutableCasesList.postValue(tempList);

            mutableIsFirstPlayerPlaying.postValue(!isFirstPlayer);

            this.createIntegerListAndCheckWin();
        }
    }

    private void createIntegerListAndCheckWin() {
        List<Case> cases = mutableCasesList.getValue();

        if (cases != null) {
            List<Case> firstRow = Arrays.asList(
                    cases.get(0),
                    cases.get(1),
                    cases.get(2)
            );

            List<Case> secondRow = Arrays.asList(
                    cases.get(3),
                    cases.get(4),
                    cases.get(5)
            );

            List<Case> thirdRow = Arrays.asList(
                    cases.get(6),
                    cases.get(7),
                    cases.get(8)
            );

            List<List<Case>> casesListTemp = Arrays.asList(firstRow, secondRow, thirdRow);

            Integer integer = Case.checkWinner(casesListTemp);
            this.mutableWhoIsWinner.postValue(integer);
        }
    }
}

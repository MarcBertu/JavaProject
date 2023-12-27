package com.example.javaproject.game;

import androidx.annotation.NonNull;

import java.util.List;

public class Case {

    public enum CaseState { X, O, NONE }

    private CaseState state = CaseState.NONE;

    public CaseState getState() {
        return state;
    }

    public void setState(CaseState state) {
        this.state = state;
    }

    private static final int[][] magicSquare = {
            {8, 1, 6},
            {3, 5, 7},
            {4, 9, 2}
    };

    public static int checkWinner(List<List<Case>> board) {
        int[] sums = new int[8];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Case.CaseState cellState = board.get(i).get(j).getState();
                int cellValue = 0;
                if (cellState.equals(CaseState.X)) {
                    cellValue = 1;
                }
                else if (cellState.equals(CaseState.O)) {
                    cellValue = 2;
                }
                sums[i] += magicSquare[i][j] * cellValue;
                sums[j + 3] += magicSquare[i][j] * cellValue;

                if (i == j) {
                    sums[6] += magicSquare[i][j] * cellValue;
                }
                if (i + j == 2) {
                    sums[7] += magicSquare[i][j] * cellValue;
                }
            }
        }

        for (int sum : sums) {
            if (sum == 15) {
                return 1;
            } else if (sum == 30) {
                return 2;
            }
        }

        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "Case{" +
                "state=" + state +
                '}';
    }
}

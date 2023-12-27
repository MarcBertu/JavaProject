package com.example.javaproject.utils;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaproject.R;
import com.example.javaproject.game.Case;
import com.example.javaproject.game.CaseAdapter;

import java.util.List;

public class BindingAdapters {

    private BindingAdapters() {
        // Required empty constructor
    }

    @BindingAdapter({"updateWaitedPlayer", "viewModel"})
    public static void updateWaitedPlayer(TextView textView, Boolean isFirstPlayer, GameViewModel viewModel) {
        String waitingText =
                String.format(
                        "Waiting player %s to play",
                        Boolean.TRUE.equals(isFirstPlayer) ? viewModel.firstPlayer.getValue() : viewModel.secondPlayer.getValue()
                );
        textView.setText(waitingText);
    }

    @BindingAdapter("updateDataAdapter")
    public static void updateDataAdapter(RecyclerView recyclerView, List<Case> data) {
        CaseAdapter adapter = (CaseAdapter) recyclerView.getAdapter();
        if(adapter != null) {
            adapter.submitList(data);
            adapter.notifyItemRangeChanged(0, adapter.getItemCount());
        }
    }

    @BindingAdapter("updateCaseState")
    public static void updateCaseState(ImageView imageView, Case.CaseState state) {
        switch (state) {
            case O:
                imageView.setImageResource(R.drawable.circle);
                break;
            case X:
                imageView.setImageResource(R.drawable.cross);
                break;
            case NONE:
            default:
                imageView.setImageResource(R.drawable.empty_case);
                break;
        }
    }
}

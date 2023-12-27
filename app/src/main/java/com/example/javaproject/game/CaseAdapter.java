package com.example.javaproject.game;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javaproject.databinding.GameCaseItemBinding;
import com.example.javaproject.utils.GameViewModel;

public class CaseAdapter extends ListAdapter<Case, CaseAdapter.CaseViewHolder> {

    private final GameViewModel viewModel;

    protected CaseAdapter(GameViewModel viewModel) {
        super(DIFF_CALLBACK);
        this.viewModel = viewModel;
    }

    static class CaseViewHolder extends RecyclerView.ViewHolder {

        private final GameCaseItemBinding binding;
        private final ImageButton imageButton;

        public CaseViewHolder(@NonNull GameCaseItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            this.imageButton = binding.caseBloc;
        }

        public void bind(Case item) {
            binding.setData(item);
            binding.executePendingBindings();
        }
    }

    public static final DiffUtil.ItemCallback<Case> DIFF_CALLBACK = new DiffUtil.ItemCallback<Case>() {
        @Override
        public boolean areItemsTheSame(@NonNull Case oldItem, @NonNull Case newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Case oldItem, @NonNull Case newItem) {
            return false;
        }
    };

    @NonNull
    @Override
    public CaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CaseViewHolder(
                GameCaseItemBinding.inflate(LayoutInflater.from(parent.getContext()))
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CaseViewHolder holder, int position) {
        Case item = getItem(position);

        if (item != null) {
            holder.bind(item);

            holder.imageButton.setOnClickListener(c -> this.viewModel.childrenUpdatePosition(position));

            if (!item.getState().equals(Case.CaseState.NONE)) {
                holder.itemView.setClickable(false);
                holder.itemView.setEnabled(false);
            }
        }
    }


}

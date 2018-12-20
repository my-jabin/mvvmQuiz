package com.jiujiu.question.ui.statistic.difficulty;

import com.jiujiu.question.R;
import com.jiujiu.question.data.model.DifficultyStatistic;
import com.jiujiu.question.databinding.StatisticItemDifficultyBinding;
import com.jiujiu.question.ui.statistic.StatisticBaseAdapter;

import java.text.DecimalFormat;

import androidx.recyclerview.widget.RecyclerView;

public class StatisticDifficultyAdapter extends StatisticBaseAdapter<DifficultyStatistic,StatisticItemDifficultyBinding,StatisticDifficultyAdapter.DifficultyViewHolder> {

    @Override
    public DifficultyViewHolder generateViewHolder(StatisticItemDifficultyBinding binding) {
        return new DifficultyViewHolder(binding);
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.statistic_item_difficulty;
    }

    @Override
    public void bindsViewHolder(DifficultyViewHolder holder, DifficultyStatistic data) {
        holder.binding.tvItemDifficulty.setText(data.difficulty.toString());
        int percentage = data.answeringTime > 0 ? data.correctTime * 100 / data.answeringTime : 0;
        holder.binding.tvCorrectness.setText(getContext().getString(R.string.str_correctness, percentage));
        holder.binding.difficultyItemProgressbar.setMax(data.answeringTime);
        holder.binding.difficultyItemProgressbar.setProgress(data.correctTime);
        holder.binding.tvProgress.setText( getContext().getString(R.string.str_progress_text,data.correctTime,data.answeringTime));

    }

    @Override
    public boolean areItemTheSame(DifficultyStatistic newObject, DifficultyStatistic oldObject) {
        return newObject.difficulty == oldObject.difficulty;
    }

    @Override
    public boolean areContentTheSame(DifficultyStatistic newObject, DifficultyStatistic oldObject) {
        return newObject.difficulty == oldObject.difficulty &&
                newObject.answeringTime == oldObject.answeringTime &&
                newObject.correctTime == oldObject.correctTime;
    }

    public static class DifficultyViewHolder extends RecyclerView.ViewHolder {
        StatisticItemDifficultyBinding binding;

        public DifficultyViewHolder(StatisticItemDifficultyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

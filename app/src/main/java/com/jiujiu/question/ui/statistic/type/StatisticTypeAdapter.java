package com.jiujiu.question.ui.statistic.type;

import com.jiujiu.question.R;
import com.jiujiu.question.data.model.TypeStatistic;
import com.jiujiu.question.databinding.StatisticItemTypeBinding;
import com.jiujiu.question.ui.statistic.StatisticBaseAdapter;

import java.text.DecimalFormat;

import androidx.recyclerview.widget.RecyclerView;

public class StatisticTypeAdapter extends StatisticBaseAdapter<TypeStatistic, StatisticItemTypeBinding, StatisticTypeAdapter.TypeViewHolder> {

    @Override
    public TypeViewHolder generateViewHolder(StatisticItemTypeBinding binding) {
        return new TypeViewHolder(binding);
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.statistic_item_type;
    }

    @Override
    public void bindsViewHolder(TypeViewHolder holder, TypeStatistic data) {
        holder.binding.tvItemType.setText(data.type.toString());
        int percentage = data.answeringTime > 0 ? data.correctTime * 100 / data.answeringTime : 0;
        holder.binding.tvCorrectness.setText(getContext().getString(R.string.str_correctness, percentage));
        holder.binding.typeItemProgressbar.setMax(data.answeringTime);
        holder.binding.typeItemProgressbar.setProgress(data.correctTime);
        holder.binding.tvProgress.setText( getContext().getString(R.string.str_progress_text,data.correctTime,data.answeringTime));

    }

    @Override
    public boolean areItemTheSame(TypeStatistic oldObject, TypeStatistic newObject) {
        return newObject.type == oldObject.type;
    }

    @Override
    public boolean areContentTheSame(TypeStatistic oldObject, TypeStatistic newObject) {
        return oldObject.type == newObject.type &&
                oldObject.answeringTime == newObject.answeringTime &&
                oldObject.correctTime == newObject.correctTime;
    }

    public static class TypeViewHolder extends RecyclerView.ViewHolder {
        StatisticItemTypeBinding binding;

        public TypeViewHolder(StatisticItemTypeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

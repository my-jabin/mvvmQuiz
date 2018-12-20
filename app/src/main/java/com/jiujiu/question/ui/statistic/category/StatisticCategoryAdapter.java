package com.jiujiu.question.ui.statistic.category;

import com.jiujiu.question.R;
import com.jiujiu.question.data.model.CategoryStatistic;
import com.jiujiu.question.databinding.StatisticItemCategoryBinding;
import com.jiujiu.question.ui.statistic.StatisticBaseAdapter;

import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;

public class StatisticCategoryAdapter extends StatisticBaseAdapter<CategoryStatistic, StatisticItemCategoryBinding, StatisticCategoryAdapter.CategoryViewHolder> {
    @Override
    public CategoryViewHolder generateViewHolder(StatisticItemCategoryBinding binding) {
        return new CategoryViewHolder(binding);
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.statistic_item_category;
    }

    @Override
    public void bindsViewHolder(CategoryViewHolder holder, CategoryStatistic data) {
        String categoryStr = data.category.replace("Entertainment:","").trim();
        holder.binding.tvItemCategory.setText(categoryStr);
        int percentage = data.answeringTime > 0 ? data.correctTime * 100 / data.answeringTime : 0;
        holder.binding.tvCorrectness.setText(getContext().getString(R.string.str_correctness, percentage));
        holder.binding.categoryItemProgressbar.setMax(data.total);
        holder.binding.categoryItemProgressbar.setProgress(data.answeringTime);
        holder.binding.categoryItemProgressbar.setSecondaryProgress(data.correctTime);
        holder.binding.tvProgress.setText(getContext().getString(R.string.str_progress_text, data.answeringTime, data.total));
    }

    @Override
    public boolean areItemTheSame(CategoryStatistic oldObject, CategoryStatistic newObject) {
        return Objects.equals(newObject.category, oldObject.category);
    }

    @Override
    public boolean areContentTheSame(CategoryStatistic oldObject, CategoryStatistic newObject) {
        return Objects.equals(oldObject.category, newObject.category) &&
                oldObject.total == newObject.total &&
                oldObject.correctTime == newObject.correctTime &&
                oldObject.answeringTime == newObject.answeringTime;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        StatisticItemCategoryBinding binding;

        public CategoryViewHolder(StatisticItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

package com.jiujiu.question.ui.statistic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public abstract class StatisticBaseAdapter<T, B extends ViewDataBinding, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    private List<T> mStatistic;
    private Context mContext;

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        B binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), getItemLayoutId(), parent, false);
        return generateViewHolder(binding);
    }

    public Context getContext() {
        return mContext;
    }

    public abstract V generateViewHolder(B binding);

    @LayoutRes
    public abstract int getItemLayoutId();

    @Override
    public void onBindViewHolder(V holder, int position) {
        if (mStatistic != null) {
            bindsViewHolder(holder, mStatistic.get(position));
        }
    }

    public abstract void bindsViewHolder(V holder, T data);

    public void setStatistic(List<T> statistic) {
        if (statistic == null || statistic.size() == 0) return;
        if (this.mStatistic == null || this.mStatistic.size() == 0) {
            this.mStatistic = statistic;
            notifyItemRangeInserted(0, statistic.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mStatistic.size();
                }

                @Override
                public int getNewListSize() {
                    return statistic.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return areItemTheSame(mStatistic.get(oldItemPosition), statistic.get(newItemPosition));
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return areContentTheSame(mStatistic.get(oldItemPosition), statistic.get(newItemPosition));
                }
            });
            this.mStatistic = statistic;
            result.dispatchUpdatesTo(this);
        }
    }

    public abstract boolean areItemTheSame(T oldObject, T newObject);

    public abstract boolean areContentTheSame(T oldObject, T newObject);

    @Override
    public int getItemCount() {
        return mStatistic == null ? 0 : mStatistic.size();
    }
}

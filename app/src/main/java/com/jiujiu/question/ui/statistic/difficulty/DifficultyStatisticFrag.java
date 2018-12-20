package com.jiujiu.question.ui.statistic.difficulty;

import com.jiujiu.question.R;
import com.jiujiu.question.databinding.StatisticFragDifficultyBinding;
import com.jiujiu.question.ui.statistic.StatisticBaseFragment;

public class DifficultyStatisticFrag extends StatisticBaseFragment<StatisticFragDifficultyBinding, StatisticDifficultyAdapter> {

    @Override
    protected StatisticDifficultyAdapter generateAdapter() {
        return new StatisticDifficultyAdapter();
    }

    @Override
    protected void setupViewModel() {
        getViewModel().getDifficultyStatistic().observe(getActivity(), statistics -> {
            getViewModel().setIsLoading(false);
            getAdapter().setStatistic(statistics);
        });
    }

    @Override
    protected void setupAdapter() {
        getBinding().difficultyRecyclerView.setAdapter(getAdapter());
    }

    @Override
    protected void startLoading() {
        getViewModel().loadingDifficultyStatistic();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.statistic_frag_difficulty;
    }
}

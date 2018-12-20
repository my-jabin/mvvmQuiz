package com.jiujiu.question.ui.statistic.type;

import com.jiujiu.question.R;
import com.jiujiu.question.databinding.StatisticFragTypeBinding;
import com.jiujiu.question.ui.statistic.StatisticBaseFragment;

public class TypeStatisticFrag extends StatisticBaseFragment<StatisticFragTypeBinding, StatisticTypeAdapter> {

    @Override
    protected StatisticTypeAdapter generateAdapter() {
        return new StatisticTypeAdapter();
    }

    @Override
    protected void setupViewModel() {
        getViewModel().getTypeStatistic().observe(getActivity(), statistics -> {
            getViewModel().setIsLoading(false);
            getAdapter().setStatistic(statistics);
        });
    }

    @Override
    protected void setupAdapter() {
        getBinding().typeRecyclerView.setAdapter(getAdapter());
    }

    @Override
    protected void startLoading() {
        getViewModel().loadingTypeStatistic();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.statistic_frag_type;
    }
}

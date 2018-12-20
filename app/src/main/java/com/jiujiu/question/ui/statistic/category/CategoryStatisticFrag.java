package com.jiujiu.question.ui.statistic.category;

import com.jiujiu.question.R;
import com.jiujiu.question.databinding.StatisticFragCategoryBinding;
import com.jiujiu.question.ui.statistic.StatisticBaseFragment;

public class CategoryStatisticFrag extends StatisticBaseFragment<StatisticFragCategoryBinding, StatisticCategoryAdapter> {

    @Override
    protected StatisticCategoryAdapter generateAdapter() {
        return new StatisticCategoryAdapter();
    }

    @Override
    protected void setupViewModel() {
        getViewModel().getCategoryStatistic().observe(getActivity(),categoryStatistics ->{
            getViewModel().setIsLoading(false);
            getAdapter().setStatistic(categoryStatistics);
        });
    }

    @Override
    protected void setupAdapter() {
        getBinding().categoryRecyclerView.setAdapter(getAdapter());
    }

    @Override
    protected void startLoading() {
        getViewModel().loadingCategoryStatistic();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.statistic_frag_category;
    }

}

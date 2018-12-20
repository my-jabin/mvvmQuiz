package com.jiujiu.question.ui.statistic;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jiujiu.question.BR;
import com.jiujiu.question.ui.base.BaseFragment;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

public abstract class StatisticBaseFragment<T extends ViewDataBinding, A extends StatisticBaseAdapter> extends BaseFragment<T , StatisticActivityViewModel> {

    protected final String TAG = this.getClass().getSimpleName();

    private A mAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        mAdapter = generateAdapter();
        setupAdapter();
        setupViewModel();
    }

    protected abstract A generateAdapter();

    public A getAdapter(){
        return this.mAdapter;
    }

    protected abstract void setupViewModel();

    protected abstract void setupAdapter();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
        startLoading();
    }

    protected abstract void startLoading();

    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }


    @Override
    protected StatisticActivityViewModel generateViewmodel() {
        return ViewModelProviders.of(getActivity()).get(StatisticActivityViewModel.class);
    }

}

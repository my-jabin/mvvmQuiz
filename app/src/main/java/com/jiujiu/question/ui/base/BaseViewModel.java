package com.jiujiu.question.ui.base;

import androidx.lifecycle.ViewModel;
import androidx.databinding.ObservableBoolean;

import com.jiujiu.question.data.DataManager;

import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {

    private CompositeDisposable mDisposable;
    private final DataManager mDataManager;
    private ObservableBoolean isLoading = new ObservableBoolean(false);

    public BaseViewModel(DataManager dataManager) {
        mDisposable = new CompositeDisposable();
        this.mDataManager = dataManager;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading.set(isLoading);
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mDisposable;
    }

    @Override
    protected void onCleared() {
        mDisposable.dispose();
        super.onCleared();
    }
}

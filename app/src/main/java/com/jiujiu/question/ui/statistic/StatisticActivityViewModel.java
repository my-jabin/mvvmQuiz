package com.jiujiu.question.ui.statistic;

import com.jiujiu.question.data.DataManager;
import com.jiujiu.question.data.model.CategoryStatistic;
import com.jiujiu.question.data.model.DifficultyStatistic;
import com.jiujiu.question.data.model.TypeStatistic;
import com.jiujiu.question.ui.base.BaseViewModel;

import java.sql.Types;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.disposables.Disposable;

public class StatisticActivityViewModel extends BaseViewModel {

    private MutableLiveData<List<CategoryStatistic>> mCategoryStatistic = new MutableLiveData<>();
    private MutableLiveData<List<DifficultyStatistic>> mDifficultyStatistic = new MutableLiveData<>();
    private MutableLiveData<List<TypeStatistic>> mTypeStatistic = new MutableLiveData<>();

    @Inject
    public StatisticActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void loadingCategoryStatistic() {
        setIsLoading(true);
        getCompositeDisposable().add(
                getDataManager().getCategoryStatistic().subscribe(statistics -> {
                    mCategoryStatistic.postValue(statistics);
                }, throwable -> {
                })
        );
    }

    public void loadingDifficultyStatistic() {
        setIsLoading(true);
        getCompositeDisposable().add(
                getDataManager().getDifficultyStatistic().subscribe(statistics -> {
                    mDifficultyStatistic.postValue(statistics);
                }, throwable -> {
                })
        );
    }

    public void loadingTypeStatistic() {
        setIsLoading(true);
        getCompositeDisposable().add(
                getDataManager().getTypeStatistic().subscribe(statistics -> {
                    mTypeStatistic.postValue(statistics);
                }, throwable -> {
                })
        );
    }


    public LiveData<List<CategoryStatistic>> getCategoryStatistic() {
        return mCategoryStatistic;
    }

    public LiveData<List<DifficultyStatistic>> getDifficultyStatistic() {
        return mDifficultyStatistic;
    }

    public LiveData<List<TypeStatistic>> getTypeStatistic() {
        return mTypeStatistic;
    }
}

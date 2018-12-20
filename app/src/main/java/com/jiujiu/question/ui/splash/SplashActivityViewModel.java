package com.jiujiu.question.ui.splash;

import androidx.lifecycle.LiveData;

import com.jiujiu.question.data.DataManager;
import com.jiujiu.question.ui.base.BaseViewModel;
import com.jiujiu.question.util.SingleLiveEvent;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashActivityViewModel extends BaseViewModel {

    private SingleLiveEvent<Void> openMainActivityEvent = new SingleLiveEvent<>();

    @Inject
    public SplashActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void startSeeding() {
        getCompositeDisposable().add(
                Completable.fromRunnable(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> openMainActivityEvent.call()));
    }

    public void setCurrentUserName(String userName) {
        getDataManager().setCurrentUserName(userName);
    }

    public LiveData<Void> getOpenMainActivityEvent(){
        return this.openMainActivityEvent;
    }
}

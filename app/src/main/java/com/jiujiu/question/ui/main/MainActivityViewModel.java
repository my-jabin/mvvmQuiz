package com.jiujiu.question.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.util.Log;

import com.jiujiu.question.data.DataManager;
import com.jiujiu.question.data.model.Difficulty;
import com.jiujiu.question.data.model.Type;
import com.jiujiu.question.ui.base.BaseViewModel;
import com.jiujiu.question.util.AppConstant;
import com.jiujiu.question.util.SingleLiveEvent;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class MainActivityViewModel extends BaseViewModel {
    private static final String TAG = "MainActivityViewModel";
    private List<String> categoryName;

    private MutableLiveData<String> mErrorMessage = new MutableLiveData<>();
    private MutableLiveData<Integer> mUnfinishedCount = new MutableLiveData<>();

//    private MutableLiveData<List<Difficulty>> mDifficultyList = new MutableLiveData<>();
//    private MutableLiveData<List<Type>> mTypeList = new MutableLiveData<>();
//    private MutableLiveData<List<Integer>> mAmountList = new MutableLiveData<>();
    private MutableLiveData<List<String>> mCategoryList = new MutableLiveData<>();

    private SingleLiveEvent<Bundle> openQuizActivityEvent = new SingleLiveEvent<>();


    @Inject
    public MainActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void fetchQuestionOptions() {
        setIsLoading(true);
        Disposable d = getDataManager().getCategoriesOptions()
                .subscribe(categoryEntities -> {
                    categoryName = categoryEntities.stream().map(entity -> entity.name).collect(Collectors.toList());
                    mCategoryList.postValue(categoryName);
//                    mDifficultyList.postValue(getDataManager().getDifficultyOptions());
//                    mTypeList.postValue(getDataManager().getTypeOptions());
//                    mAmountList.postValue(getDataManager().getAmountOptions());
                    setIsLoading(false);
                }, error -> {
                    mErrorMessage.postValue(error.getMessage());
                    setIsLoading(false);
                });
        getCompositeDisposable().add(d);
    }

    public void startQuiz(int categoryIndex, Difficulty difficulty, Type type, int amount) {
        Log.d(TAG, "startQuiz: ");
        String category = categoryName == null || categoryName.size() == 0 ? "" : categoryName.get(categoryIndex);
//        Difficulty difficulty = getDataManager().getDifficultyOptions().get(difficultyIndex);
//        Type type = getDataManager().getTypeOptions().get(typeIndex);
//        int amount = getDataManager().getAmountOptions().get(amountIndex);
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.INTENT_QUIZ_MODE, AppConstant.QUIZ_MODE_NEW);
        bundle.putString(AppConstant.INTENT_QUIZ_CATEGORY, category);
        bundle.putString(AppConstant.INTENT_QUIZ_DIFFICULTY, difficulty.toString());
        bundle.putString(AppConstant.INTENT_QUIZ_TYPE, type.toString());
        bundle.putInt(AppConstant.INTENT_QUIZ_AMOUNT, amount);
        openQuizActivityEvent.setValue(bundle);

    }

    public void checkAbortion() {
        Disposable d = getDataManager().hasUnFinishedQuestion()
                .subscribe(count -> {
                    if (count > 0)
                        mUnfinishedCount.postValue(count);
                    else {
                        fetchQuestionOptions();
                    }
                }, throwable -> mErrorMessage.postValue(throwable.getMessage()));
        getCompositeDisposable().add(d);
    }

    public LiveData<Integer> getUnfinishedCount() {
        return mUnfinishedCount;
    }

//    public LiveData<List<Difficulty>> getDifficultyList() {
//        return mDifficultyList;
//    }
//
//    public LiveData<List<Type>> getTypeList() {
//        return mTypeList;
//    }
//
//    public LiveData<List<Integer>> getAmountList() {
//        return mAmountList;
//    }

    public LiveData<List<String>> getCategoryList() {
        return mCategoryList;
    }

    public LiveData<String> getErrorMessage() {
        return this.mErrorMessage;
    }

    public LiveData<Bundle> getOpenQuizActivityEvent() {
        return this.openQuizActivityEvent;
    }

    public void continueWithUnfinished() {
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.INTENT_QUIZ_MODE, AppConstant.QUIZ_MODE_ABORD);
        openQuizActivityEvent.setValue(bundle);
    }

    public void startNewQuiz() {
        fetchQuestionOptions();
        getCompositeDisposable().add(getDataManager().cleanUnfinishedQuiz().subscribe(() -> {
        }, error -> mErrorMessage.postValue(error.getMessage())));
    }
}

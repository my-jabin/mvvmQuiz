package com.jiujiu.question.ui.quiz;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jiujiu.question.data.DataManager;
import com.jiujiu.question.data.model.QuestionEntity;
import com.jiujiu.question.data.model.Status;
import com.jiujiu.question.ui.base.BaseViewModel;
import com.jiujiu.question.util.SingleLiveEvent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class QuizActivityViewModel extends BaseViewModel {

    private static final String TAG = "QuizActivityViewModel";
    private MutableLiveData<List<QuestionEntity>> mQuestionList = new MutableLiveData<>();
    private MutableLiveData<String> mErrorMessage = new MutableLiveData<>();
    private SingleLiveEvent<Void> mBackToParentActivityEvent = new SingleLiveEvent<>();

    private int mQuizMode = 0;

    @Inject
    public QuizActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    // load new question from remote server
    public void loadQuestions(String category, String difficulty, String type, int amount) {
        setIsLoading(true);
        Disposable d = getDataManager().loadQuestions(category, difficulty, type, amount)
                .subscribe(resource -> {
                    if (resource.getStatus() == Status.SUCCESS) {
                        int totalAmount = resource.getData().size();
                        getDataManager().setQuestionAmount(totalAmount);
                        getDataManager().setRightAnswerAmount(0);
                        getDataManager().setWrongAnswerAmount(0);
                        mQuestionList.postValue(resource.getData());
                    } else {
                        mErrorMessage.postValue(resource.getMessage());
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    mErrorMessage.postValue(throwable.getMessage());
                });
        getCompositeDisposable().add(d);
    }

    public void loadAnsweringQuestions() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().getAnsweringQuestion()
                .subscribe(entities -> {
                    mQuestionList.postValue(entities);
                    setIsLoading(false);
                }, throwable -> {
                    mErrorMessage.postValue(throwable.getMessage());
                    setIsLoading(false);
                }));
    }

    public LiveData<List<QuestionEntity>> getQuestions() {
        return mQuestionList;
    }

    public LiveData<String> getErrorMessage() {
        return mErrorMessage;
    }

    public LiveData<Void> getBackToParentActivityEvent() {
        return this.mBackToParentActivityEvent;
    }

    public void onTryAgainClick() {
        mBackToParentActivityEvent.call();
    }

    public void onRightAnswerClick(QuestionEntity entity) {
        entity.isAnswering = false;
        entity.correctTimes++;
        entity.answeringTimes++;
        int amount = getDataManager().getRightAnswerAmount();
        getDataManager().setRightAnswerAmount(++amount);
        getCompositeDisposable().add(getDataManager().updateQuestion(entity)
                .subscribe(() -> {
                }, error -> mErrorMessage.postValue(error.getMessage())));
    }

    public void onWrongAnswerClick(QuestionEntity entity) {
        entity.isAnswering = false;
        entity.wrongTimes++;
        entity.answeringTimes++;
        int amount = getDataManager().getWrongAnswerAmount();
        getDataManager().setWrongAnswerAmount(++amount);
        getCompositeDisposable().add(getDataManager().updateQuestion(entity)
                .subscribe(() -> {
                }, error -> mErrorMessage.postValue(error.getMessage())));
    }

    public int getRightAnswerAmount() {
        return getDataManager().getRightAnswerAmount();
    }

    public int getWrongAnswerAmount() {
        return getDataManager().getWrongAnswerAmount();
    }

    public int getTotalQuestionAmount() {
        return getDataManager().getQuestionAmount();
    }

    public void setQuizMode(int mode){
        this.mQuizMode = mode;
    }

    public int getQuizMode(){
        return this.mQuizMode;
    }

    public boolean hasFinished(){
        return getDataManager().getRightAnswerAmount() + getDataManager().getWrongAnswerAmount() == getDataManager().getQuestionAmount();
    }

    public void cleanUnFinishedQuiz() {
        getCompositeDisposable().add(getDataManager().cleanUnfinishedQuiz().subscribe(() -> {
        }, error -> mErrorMessage.postValue(error.getMessage())));
    }
}

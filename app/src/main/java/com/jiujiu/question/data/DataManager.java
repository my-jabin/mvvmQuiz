package com.jiujiu.question.data;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiujiu.question.data.local.LocalDataSource;
import com.jiujiu.question.data.model.CategoryEntity;
import com.jiujiu.question.data.model.CategoryStatistic;
import com.jiujiu.question.data.model.Difficulty;
import com.jiujiu.question.data.model.DifficultyStatistic;
import com.jiujiu.question.data.model.QuestionEntity;
import com.jiujiu.question.data.model.Resource;
import com.jiujiu.question.data.model.Type;
import com.jiujiu.question.data.model.TypeStatistic;
import com.jiujiu.question.data.remote.ApiResponse;
import com.jiujiu.question.data.remote.RemoteDataSource;
import com.jiujiu.question.di.ApplicationContext;
import com.jiujiu.question.util.Util;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class DataManager {
    private static final String TAG = "DataManager";
    private AppPreference mPreference;
    private LocalDataSource mLocalDataSource;
    private RemoteDataSource mRemoteDataSource;
    private Context mContext;

    @Inject
    public DataManager(@ApplicationContext Context context, AppPreference preference, LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        mContext = context;
        mPreference = preference;
        this.mLocalDataSource = localDataSource;
        this.mRemoteDataSource = remoteDataSource;
    }

    public Single<Resource<List<QuestionEntity>>> loadQuestions(String category, String difficulty, String type, int amount) {
        return mLocalDataSource.getCategoryId(category)
                .subscribeOn(Schedulers.io())
                .defaultIfEmpty(0)
                .flatMapSingle(categoryID -> {
                    String difficultyArgument = Util.matchDifffToUrlQueryString(difficulty);
                    String typeArgument = Util.matchTypeToUrlQueryString(type);
                    return mRemoteDataSource.loadQuestions(categoryID, difficultyArgument, typeArgument, amount);
                })
                .flatMap(apiResponse -> {
                    int responseCode = apiResponse.getResponseCode();
                    if (responseCode == ApiResponse.SUCCESS) {
                        List<QuestionEntity> entities =  Util.convertToQuestionEntities(apiResponse.getQuestions());
//                        return mLocalDataSource.updateOrInsertAnsweringQuestion(entities)
//                                .andThen(Single.just(Resource.success(entities)));
                        return mLocalDataSource.updateOrInsertAnsweringQuestion(entities)
                                .andThen(mLocalDataSource.loadAnsweringQuestion())
                                .flatMap(answertingQuestions -> Single.just( Resource.success(answertingQuestions)));
                    } else {
                        return Single.just(Resource.error(Util.errorMessage(responseCode), null));
                    }
                });
    }

    public String getCurrentUserName() {
        return this.mPreference.getUserName();
    }

    public void setCurrentUserName(String userName) {
        this.mPreference.setUserName(userName);
    }

    public Observable<List<CategoryEntity>> getCategoriesOptions() {
        return this.mLocalDataSource.isCategoryEmpty()
                .subscribeOn(Schedulers.io())
                .concatMap(empty -> {
                    if (empty) {
                        Gson gson = new Gson();
                        java.lang.reflect.Type type = new TypeToken<List<CategoryEntity>>() {
                        }.getType();
                        List<CategoryEntity> categoryEntities = gson.fromJson(Util.loadJSONFromAsset(mContext, "categories.json"), type);
                        return mLocalDataSource.saveCategories(categoryEntities)
                                .andThen(mLocalDataSource.loadCategories());
                    } else {
                        Log.d(TAG, "getCategoriesOptions: load from local");
                        return mLocalDataSource.loadCategories();
                    }
                });
    }

    public Single<List<CategoryStatistic>> getCategoryStatistic(){
        return mLocalDataSource.getCategoryStatistic().subscribeOn(Schedulers.io());
    }

    public Single<List<DifficultyStatistic>> getDifficultyStatistic(){
        return mLocalDataSource.getDifficultyStatistic().subscribeOn(Schedulers.io());
    }

    public Single<List<TypeStatistic>> getTypeStatistic(){
        return mLocalDataSource.getTypeStatistic().subscribeOn(Schedulers.io());
    }

    public Completable updateQuestion(QuestionEntity entity) {
        return mLocalDataSource.updateQuestion(entity).subscribeOn(Schedulers.io());
    }

    public Single<Integer> hasUnFinishedQuestion() {
        return mLocalDataSource.isAnsweringQuestionCount().subscribeOn(Schedulers.io());
    }

    public Completable cleanUnfinishedQuiz() {
        return mLocalDataSource.cleanUnfinishedQuiz().subscribeOn(Schedulers.io());
    }

    public Single<List<QuestionEntity>> getAnsweringQuestion(){
       return mLocalDataSource.loadAnsweringQuestion().subscribeOn(Schedulers.io());
    }

    public void setQuestionAmount(int amount){
        mPreference.setQuestionAmount(amount);
    }

    public int getQuestionAmount(){
        return mPreference.getQuestionAmount();
    }

    public void setRightAnswerAmount(int amount){
        mPreference.setRightAnswerAmount(amount);
    }

    public int getRightAnswerAmount(){
        return mPreference.getRightAnswerAmount();
    }

    public void setWrongAnswerAmount(int amount){
        mPreference.setWrongAnswerAmount(amount);
    }

    public int getWrongAnswerAmount(){
        return mPreference.getWrongAnswerAmount();
    }
}

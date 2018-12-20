package com.jiujiu.question.data.local;

import android.util.Log;

import com.jiujiu.question.data.local.dao.CategoryDao;
import com.jiujiu.question.data.local.dao.QuestionDao;
import com.jiujiu.question.data.model.CategoryEntity;
import com.jiujiu.question.data.model.CategoryStatistic;
import com.jiujiu.question.data.model.DifficultyStatistic;
import com.jiujiu.question.data.model.QuestionEntity;
import com.jiujiu.question.data.model.TypeStatistic;
import com.jiujiu.question.data.remote.Question;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class LocalDataSource {
    private static final String TAG = "LocalDataSource";
    private CategoryDao mCategoryDao;
    private QuestionDao mQuestionDao;

    @Inject
    public LocalDataSource(CategoryDao categoryDao, QuestionDao questionDao) {
        this.mCategoryDao = categoryDao;
        this.mQuestionDao = questionDao;
    }

    public Observable<Boolean> isCategoryEmpty() {
        return mCategoryDao.getCategoryCount()
                .map(integer -> (integer <= 0))
                .toObservable();
    }

    public Completable saveCategories(List<CategoryEntity> categoryList){
        return Completable.fromRunnable(()->{
            mCategoryDao.bulkInsert(categoryList);
        });
    }

    public Observable<List<CategoryEntity>> loadCategories(){
        return mCategoryDao.loadAllCategory().toObservable();
    }

    public Single<List<QuestionEntity>> loadAnsweringQuestion(){
        return mQuestionDao.loadAnswerings();
    }

    public Maybe<Integer> getCategoryId(String category){
        return mCategoryDao.getCategoryId(category);
    }

    public Completable updateQuestion(QuestionEntity  entity){
        return  Completable.fromRunnable( ()->{
            this.mQuestionDao.update(entity);
        });
    }

    public Completable updateOrInsert(List<QuestionEntity> questions){
        return Completable.fromRunnable(()->{
            this.mQuestionDao.updateOrInsert(questions);
        });
    }

    public Completable updateOrInsertAnsweringQuestion(List<QuestionEntity> entities){
        return Completable.fromRunnable(()->{
            this.mQuestionDao.updateOrInsertAnsweringQuestion(entities);
        });
    }

    public Single<Integer> isAnsweringQuestionCount() {
        return this.mQuestionDao.isAnsweringCount();
    }

    public Completable cleanUnfinishedQuiz() {
        return Completable.fromRunnable( ()-> this.mQuestionDao.cleanUnfinished());
    }

    public Single<List<CategoryStatistic>> getCategoryStatistic(){
        return this.mCategoryDao.getCategoryStatistic();
    }

    public Single<List<DifficultyStatistic>> getDifficultyStatistic(){
        return this.mQuestionDao.getDifficultyStatistic();
    }

    public Single<List<TypeStatistic>> getTypeStatistic(){
        return this.mQuestionDao.getTypeStatistic();
    }
}

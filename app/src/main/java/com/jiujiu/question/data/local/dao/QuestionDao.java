package com.jiujiu.question.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.jiujiu.question.data.model.DifficultyStatistic;
import com.jiujiu.question.data.model.QuestionEntity;
import com.jiujiu.question.data.model.TypeStatistic;
import com.jiujiu.question.data.remote.Question;


import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public abstract class QuestionDao {

    @Query("select * from questions where question = :question")
    public abstract Maybe<QuestionEntity> loadQuestions(String question);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertIfNotExist(QuestionEntity entity);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void bulkInsertIfNotExist(List<QuestionEntity> entityList);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(QuestionEntity entity);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void bulkUpdate(List<QuestionEntity> entities);

    @Query("UPDATE questions set is_answering = 'true' where question in (:questionids)")
    public abstract void updateIsAnswering(List<String> questionids);

    @Transaction
    public void updateOrInsert(List<QuestionEntity> questions) {
        bulkUpdate(questions);
        bulkInsertIfNotExist(questions);
    }

    @Transaction
    public void updateOrInsertAnsweringQuestion(List<QuestionEntity> entities) {
        bulkInsertIfNotExist(entities);
        List<String> questionids = entities.stream().map(entity -> entity.question).collect(Collectors.toList());
        updateIsAnswering(questionids);
    }

    @Query("select * from questions where is_answering = 'true' ")
    public abstract Single<List<QuestionEntity>> loadAnswerings();

    @Query("select count(*) from questions where is_answering = 'true' ")
    public abstract Single<Integer> isAnsweringCount();

    @Query("update questions set is_answering= '0' where is_answering= 'true' ")
    public abstract void cleanUnfinished();


    @Query("select q.difficulty, sum(q.answering_times) as 'answeringTime' ,sum(q.correct_times) as 'correctTime' " +
            "from  questions q " +
            "group by q.difficulty")
    public abstract Single<List<DifficultyStatistic>> getDifficultyStatistic();

    @Query("select q.type, sum(q.answering_times) as 'answeringTime' ,sum(q.correct_times) as 'correctTime' " +
            "from  questions q " +
            "group by q.type")
    public abstract Single<List<TypeStatistic>> getTypeStatistic();
}

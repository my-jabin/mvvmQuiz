package com.jiujiu.question.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jiujiu.question.data.model.CategoryEntity;
import com.jiujiu.question.data.model.CategoryStatistic;
import com.jiujiu.question.data.model.DifficultyStatistic;
import com.jiujiu.question.data.model.TypeStatistic;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CategoryEntity category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<CategoryEntity> categoryList);

    @Query("select * from category")
    Maybe<List<CategoryEntity>> loadAllCategory();

    @Query("select count(*) from category")
    Single<Integer> getCategoryCount();

    @Query("select id from category where name = :category")
    Maybe<Integer> getCategoryId(String category);

    @Query("select c.name as 'category',c.totalNumberQuestions as 'total',sum(q.answering_times) as 'answeringTime' ,sum(q.correct_times) as 'correctTime' " +
            "from category c left join questions q on c.name = q.category " +
            "where c.id != 0 " +
            "group by c.name " +
            "order by answeringTime desc")
    Single<List<CategoryStatistic>> getCategoryStatistic();

}

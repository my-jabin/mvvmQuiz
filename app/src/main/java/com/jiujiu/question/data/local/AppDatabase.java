package com.jiujiu.question.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jiujiu.question.data.local.dao.CategoryDao;
import com.jiujiu.question.data.local.dao.QuestionDao;
import com.jiujiu.question.data.model.CategoryEntity;
import com.jiujiu.question.data.model.QuestionEntity;
import com.jiujiu.question.util.Converter;

@Database(entities = {CategoryEntity.class, QuestionEntity.class}, version = 2)
@TypeConverters({Converter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract QuestionDao questionDao();
}

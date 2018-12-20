package com.jiujiu.question.di.module;

import android.app.Application;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiujiu.question.data.local.AppDatabase;
import com.jiujiu.question.data.local.dao.CategoryDao;
import com.jiujiu.question.data.local.dao.QuestionDao;
import com.jiujiu.question.data.model.CategoryEntity;
import com.jiujiu.question.di.ApplicationContext;
import com.jiujiu.question.di.DatabaseInfo;
import com.jiujiu.question.di.PreferenceInfo;
import com.jiujiu.question.util.AppConstant;
import com.jiujiu.question.util.Util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Completable;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @DatabaseInfo
    public String provideDatabaseName() {
        return AppConstant.DATABASENAME;
    }

    @Provides
    @PreferenceInfo
    public String providePreferenceName() {
        return AppConstant.PREFERENCENAME;
    }

    @Provides
    @ApplicationContext
    public Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    public AppDatabase provideAppDatabase(@ApplicationContext Context context, @DatabaseInfo String databaseName) {
        return Room.databaseBuilder(context, AppDatabase.class, databaseName).build();
    }

    @Provides
    @Singleton
    public CategoryDao provideCategoryDao(AppDatabase database) {
        return database.categoryDao();
    }

    @Provides
    @Singleton
    public QuestionDao provideQuestionDao(AppDatabase database){
        return database.questionDao();
    }
}

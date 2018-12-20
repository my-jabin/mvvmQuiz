package com.jiujiu.question;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.jiujiu.question.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MvvmApp extends Application implements HasActivityInjector{

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        AndroidNetworking.initialize(getApplicationContext());
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}

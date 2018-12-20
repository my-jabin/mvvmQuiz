package com.jiujiu.question.di.builder;

import com.jiujiu.question.di.module.StatisticActivityModule;
import com.jiujiu.question.ui.login.LoginActivity;
import com.jiujiu.question.ui.main.MainActivity;
import com.jiujiu.question.ui.quiz.QuizActivity;
import com.jiujiu.question.ui.splash.SplashActivity;
import com.jiujiu.question.ui.statistic.StatisticActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    //    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    @ContributesAndroidInjector()
    abstract SplashActivity bindsSplashActivity();

    @ContributesAndroidInjector()
    abstract MainActivity bindsMainActivity();

    @ContributesAndroidInjector
    abstract LoginActivity bindsLoginActivity();

    @ContributesAndroidInjector
    abstract QuizActivity bindsQuizActivity();

//    @ContributesAndroidInjector(modules = StatisticActivityModule.class)
    @ContributesAndroidInjector()
    abstract StatisticActivity bindsStatisticActivity();


}

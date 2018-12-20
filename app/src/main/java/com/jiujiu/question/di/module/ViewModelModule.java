package com.jiujiu.question.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jiujiu.question.di.ViewModelFactory;
import com.jiujiu.question.di.ViewModelKey;
import com.jiujiu.question.ui.login.LoginViewModel;
import com.jiujiu.question.ui.main.MainActivityViewModel;
import com.jiujiu.question.ui.quiz.QuizActivityViewModel;
import com.jiujiu.question.ui.splash.SplashActivityViewModel;
import com.jiujiu.question.ui.statistic.StatisticActivityViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashActivityViewModel.class)
    abstract ViewModel bindsSplashActivityViewModel(SplashActivityViewModel splashActivityViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    abstract ViewModel bindsMainActivityViewModel(MainActivityViewModel mainActivityViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindsLoginActivityViewModel(LoginViewModel loginViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(QuizActivityViewModel.class)
    abstract ViewModel bindsQuizActivityViewModel(QuizActivityViewModel loginViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(StatisticActivityViewModel.class)
    abstract ViewModel bindsStatistictivityViewModel(StatisticActivityViewModel loginViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory factory);
}

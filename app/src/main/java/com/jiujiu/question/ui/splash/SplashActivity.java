package com.jiujiu.question.ui.splash;

import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.jiujiu.question.R;
import com.jiujiu.question.databinding.ActivitySplashBinding;
import com.jiujiu.question.ui.base.BaseActivity;
import com.jiujiu.question.ui.main.MainActivity;
import com.jiujiu.question.util.AppConstant;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashActivityViewModel> {

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().setCurrentUserName(AppConstant.USERNAME);
        getViewModel().startSeeding();
        getViewModel().getOpenMainActivityEvent().observe(this, aVoid -> openMainActivity());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashActivityViewModel generateViewModel() {
        return ViewModelProviders.of(this, factory).get(SplashActivityViewModel.class);
    }

    @Override
    public int getBindingVariableId() {
        return BR.viewModel;
    }


    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

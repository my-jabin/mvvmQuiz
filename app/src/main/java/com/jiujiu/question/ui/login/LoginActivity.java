package com.jiujiu.question.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.core.text.TextUtilsCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.jiujiu.question.BR;
import com.jiujiu.question.R;
import com.jiujiu.question.databinding.ActivityLoginBinding;
import com.jiujiu.question.ui.base.BaseActivity;
import com.jiujiu.question.ui.main.MainActivity;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().getOpenMainActivityEvent().observe(this,aVoid -> openMainActivity());
    }

    @Override
    protected LoginViewModel generateViewModel() {
        return ViewModelProviders.of(this, factory).get(LoginViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
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

    public void onLoginClick(View view) {
        String email = getBinding().etLoginEmail.getText().toString();
        String pwd = getBinding().edLoginPwd.getText().toString();
        if(getViewModel().isEmailAndPwdValid(email,pwd)){
            hideKeyboard();
            getViewModel().login(email, pwd);
        }else{
            Toast.makeText(this, R.string.login_error,Toast.LENGTH_SHORT).show();
        }

    }
}

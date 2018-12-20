package com.jiujiu.question.ui.login;

import androidx.lifecycle.LiveData;
import android.text.TextUtils;
import android.util.Patterns;

import com.jiujiu.question.data.DataManager;
import com.jiujiu.question.ui.base.BaseViewModel;
import com.jiujiu.question.util.SingleLiveEvent;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

import javax.inject.Inject;

public class LoginViewModel extends BaseViewModel {

    private SingleLiveEvent<Void> openMainActivityEvent = new SingleLiveEvent<>();

    @Inject
    public LoginViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public boolean isEmailAndPwdValid(String email, String pwd) {
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)){
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void login(String email, String pwd) {
        openMainActivityEvent.call();
    }

    public LiveData<Void> getOpenMainActivityEvent(){
        return this.openMainActivityEvent;
    }
}

package com.jiujiu.question.data;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.VisibleForTesting;

import com.jiujiu.question.di.ApplicationContext;
import com.jiujiu.question.di.PreferenceInfo;
import com.jiujiu.question.util.AppConstant;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppPreference {

    private static final String PREF_KEY_USER_NAME = "prefs_username";
    private static final String PREF_KEY_TOKEN = "token";
    private static final String PREF_KEY_DEFAULT_AMOUT_PER_CALL = "amout_per_call";
    private static final String PREF_KEY_QUESTION_AMOUNT = "question_amount";
    private static final String PREF_KEY_RIGHT_ANSWER_AMOUNT = "right_amount";
    private static final String PREF_KEY_WRONG_ANSWER_AMOUNT = "wrong_amount";

    private final SharedPreferences mPreference;

    @Inject
    public AppPreference(@ApplicationContext Context context, @PreferenceInfo String preInfo) {
        mPreference = context.getSharedPreferences(preInfo, Context.MODE_PRIVATE);
    }

    public void setUserName(String userName) {
        mPreference.edit().putString(PREF_KEY_USER_NAME, userName).apply();
    }

    public String getUserName() {
        return mPreference.getString(PREF_KEY_USER_NAME, null);
    }


    public void setToken(String token){
        mPreference.edit().putString(PREF_KEY_TOKEN,token).apply();
    }

    public String getToken(){
        return mPreference.getString(PREF_KEY_TOKEN,null);
    }

    public void setQuestionAmount(int amount) {
        mPreference.edit().putInt(PREF_KEY_QUESTION_AMOUNT,amount).apply();
    }

    public int getQuestionAmount(){
        return mPreference.getInt(PREF_KEY_QUESTION_AMOUNT,0);
    }

    public void setRightAnswerAmount(int amount){
        mPreference.edit().putInt(PREF_KEY_RIGHT_ANSWER_AMOUNT,amount).apply();
    }

    public int getRightAnswerAmount() {
        return mPreference.getInt(PREF_KEY_RIGHT_ANSWER_AMOUNT,0);
    }

    public void setWrongAnswerAmount(int amount){
        mPreference.edit().putInt(PREF_KEY_WRONG_ANSWER_AMOUNT,amount).apply();
    }

    public int getWrongAnswerAmount() {
        return mPreference.getInt(PREF_KEY_WRONG_ANSWER_AMOUNT,0);
    }
}

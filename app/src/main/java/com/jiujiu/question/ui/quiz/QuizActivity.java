package com.jiujiu.question.ui.quiz;

import androidx.appcompat.app.ActionBar;
import androidx.core.app.NavUtils;
import androidx.core.app.TaskStackBuilder;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Visibility;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jiujiu.question.BR;
import com.jiujiu.question.R;
import com.jiujiu.question.data.model.QuestionEntity;
import com.jiujiu.question.databinding.ActivityQuizBinding;
import com.jiujiu.question.ui.base.BaseActivity;
import com.jiujiu.question.util.AppConstant;
import com.jiujiu.question.util.ScreenUtil;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import javax.inject.Inject;

public class QuizActivity extends BaseActivity<ActivityQuizBinding, QuizActivityViewModel> implements QuestionCardView.QuestionCardClickListener {

    private static final String TAG = "QuizActivity";
    
    @Inject
    ViewModelProvider.Factory factory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startLoading();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // prevent to reload quiz
        getViewModel().setQuizMode(AppConstant.QUIZ_MODE_ABORD);
    }

    @Override
    protected void onStop() {
        cleanResouce();
        super.onStop();
    }

    private void startLoading() {
        int mode = getViewModel().getQuizMode();
        if (mode == AppConstant.QUIZ_MODE_NEW) {
            String category = getIntent().getStringExtra(AppConstant.INTENT_QUIZ_CATEGORY);
            String difficulty = getIntent().getStringExtra(AppConstant.INTENT_QUIZ_DIFFICULTY);
            String type = getIntent().getStringExtra(AppConstant.INTENT_QUIZ_TYPE);
            int amount = getIntent().getIntExtra(AppConstant.INTENT_QUIZ_AMOUNT, 5);
            getViewModel().loadQuestions(category, difficulty, type, amount);
        } else if (mode == AppConstant.QUIZ_MODE_ABORD) {
            getViewModel().loadAnsweringQuestions();
        }
    }

    private void setup() {
        setupWindowAnimations();
        setupSwipCards();
        setupLiveData();
        getViewModel().setQuizMode(getIntent().getIntExtra(AppConstant.INTENT_QUIZ_MODE, 0));
    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        Explode explode = new Explode();
        explode.setDuration(1000);
        explode.setMode(Visibility.MODE_OUT);
        getWindow().setEnterTransition(fade);
        getWindow().setReturnTransition(explode);
//        ChangeBounds changeBounds = new ChangeBounds();
//        changeBounds.setDuration(1000);
//        getWindow().setSharedElementEnterTransition(changeBounds);
    }

    private void setupLiveData() {
        getViewModel().getQuestions().observe(this, questions -> {
            if (questions != null || questions.size() != 0) {
                getBinding().swipContainer.removeAllViews();
                //int totalAmount = getViewModel().getTotalQuestionAmount();
                int answeredAmount = getViewModel().getRightAnswerAmount() + getViewModel().getWrongAnswerAmount();
                for (int i = 0; i < questions.size(); i++) {
                    getBinding().swipContainer.addView(new QuestionCardView(this, this, questions.get(i), answeredAmount + i + 1));
                }
            }
        });

        getViewModel().getErrorMessage().observe(this, message -> {
            if (!TextUtils.isEmpty(message))
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        getViewModel().getBackToParentActivityEvent().observe(this, aVoid -> {
            this.cleanResouce();
            backToParentActivity();
        });
    }

    private void setupSwipCards() {
        int width = ScreenUtil.getScreenWidth(this);
        int height = ScreenUtil.getScreenHeight(this);

        getBinding().swipContainer.getBuilder()
                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_HORIZONTAL)
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth((int) (0.90 * width))
                        .setViewHeight((int) (0.85 * height))
                        .setPaddingTop(100)
                        .setRelativeScale(0.1f)
                        .setSwipeRotationAngle(15))
                .setWidthSwipeDistFactor(4);


        getBinding().swipContainer.addItemRemoveListener(count -> {
            if (count == 0) {
                getBinding().resultCardView.setVisibility(View.VISIBLE);
                getBinding().tvRightAnswers.setText(this.getString(R.string.str_right_number, getViewModel().getRightAnswerAmount()));
                getBinding().tvWrongAnswers.setText(this.getString(R.string.str_wrong_number, getViewModel().getWrongAnswerAmount()));
                String rate = String.valueOf((getViewModel().getRightAnswerAmount()) * 100 / (getViewModel().getTotalQuestionAmount())) + "%";
                getBinding().tvCorrectRate.setText(this.getString(R.string.correct_rate, rate));
            }
        });
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if(!getViewModel().hasFinished()){
                    showAlertDiaglogFragment();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDiaglogFragment(){
        new MaterialDialog.Builder(this)
                .cancelable(false)
                .title(R.string.str_quiz)
                .content(R.string.abort_message)
                .positiveText(android.R.string.yes)
                .negativeText(android.R.string.no)
                .onPositive((dialog, which) -> onQuitQuizClick())
//                .onNegative((dialog, which) -> onCancelClick())
                .show();
    }

    private void onQuitQuizClick() {
        getViewModel().cleanUnFinishedQuiz();
        backToParentActivity();
    }


    @Override
    protected QuizActivityViewModel generateViewModel() {
        return ViewModelProviders.of(this, factory).get(QuizActivityViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_quiz;
    }

    @Override
    public int getBindingVariableId() {
        return BR.viewModel;
    }

    @Override
    protected int setTitle() {
        return R.string.str_quiz;
    }

    @Override
    public void onRightAnswerClick(QuestionEntity entity) {
        getViewModel().onRightAnswerClick(entity);
    }

    @Override
    public void onWrongAnswerClick(QuestionEntity entity) {
        getViewModel().onWrongAnswerClick(entity);
    }

    @Override
    public void lockView() {
        getBinding().swipContainer.lockViews();
    }

    @Override
    public void unLockView() {
        getBinding().swipContainer.unlockViews();
    }

    public void backToParentActivity() {
        getViewModel().setIsLoading(true);
        Intent upIntent = NavUtils.getParentActivityIntent(this);
//        upIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            Log.d(TAG, "backToParentActivity: create a new task stack");
            // This activity is NOT part of this app's task, so create a new task
            // when navigating up, with a synthesized back stack.
            TaskStackBuilder.create(this)
                    // Add all of this activity's parents to the back stack
                    .addNextIntentWithParentStack(upIntent)
                    // Navigate up to the closest parent
                    .startActivities();
        } else {
            // This activity is part of this app's task, so simply
            // navigate up to the logical parent activity.
            Log.d(TAG, "backToParentActivity: at the same task stack");
            NavUtils.navigateUpTo(this, upIntent);
        }
    }

    public void cleanResouce() {
        if (getBinding().swipContainer != null) {
            getBinding().swipContainer.removeAllViews();
        }
    }

    @Override
    public void onBackPressed() {
        if(!getViewModel().hasFinished())
            showAlertDiaglogFragment();
        else
            super.onBackPressed();
    }
}


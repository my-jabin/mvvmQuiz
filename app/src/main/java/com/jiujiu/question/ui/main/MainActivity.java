package com.jiujiu.question.ui.main;

import android.app.ActionBar;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.button.MaterialButton;
import com.jiujiu.question.BR;
import com.jiujiu.question.R;
import com.jiujiu.question.data.model.Difficulty;
import com.jiujiu.question.data.model.Type;
import com.jiujiu.question.databinding.ActivityMainBinding;
import com.jiujiu.question.ui.base.BaseActivity;
import com.jiujiu.question.ui.quiz.QuizActivity;
import com.jiujiu.question.ui.statistic.StatisticActivity;

import java.util.logging.Level;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainActivityViewModel> {
    @Inject
    ViewModelProvider.Factory factory;
    private static final String TAG = "MainActivity";
    private long clickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
        setupViewModel();
        setupDrawerLayout();
    }

    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        slide.setSlideEdge(Gravity.LEFT);
        getWindow().setExitTransition(slide);
        getWindow().setReenterTransition(slide);
//        ChangeBounds changeBounds = new ChangeBounds();
//        changeBounds.setDuration(1000);
//        getWindow().setSharedElementExitTransition(changeBounds);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getViewModel().checkAbortion();
    }

    private void setupViewModel() {
        getViewModel().getUnfinishedCount().observe(this, count -> {
            if (count > 0) {
                showDialogFragment();
            }
        });

        getViewModel().getErrorMessage().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        getViewModel().getCategoryList().observe(this, categories -> {
            getBinding().catetorySpinner.attachDataSource(categories);
        });

        getViewModel().getOpenQuizActivityEvent().observe(this, bundle -> openQuizActivity(bundle));
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    public void startQuiz(View view) {
        int categoryIndex = getBinding().catetorySpinner.getSelectedIndex();
        getViewModel().startQuiz(
                categoryIndex,
                getCheckedDifficulty(getBinding().chipDifficultyGroup.getCheckedChipId()),
                getCheckedType(getBinding().chipTypeGroup.getCheckedChipId()),
                getCheckedAmount(getBinding().chipAmountGroup.getCheckedChipId()));
    }

    private void setupDrawerLayout() {
//        getBinding().navigation.setCheckedItem(R.id.navi_quiz_item);
        getBinding().navigation.getMenu().findItem(R.id.navi_quiz_item).setChecked(true);
        getBinding().navigation.setNavigationItemSelectedListener(menuItem -> {
            getBinding().mainDrawer.closeDrawers();
            switch (menuItem.getItemId()) {
                case R.id.navi_quiz_item:
                    break;
                case R.id.navi_statistic_item:
                    openStaticActivity();
                    break;
                case R.id.navi_rate_item:
                    break;
            }
            return true;
        });
    }

    private void showDialogFragment() {
        new MaterialDialog.Builder(this)
                .cancelable(false)
                .title(R.string.str_quiz)
                .content(R.string.alert_message)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.no)
                .onPositive((dialog, which) -> onContinueClick())
                .onNegative((dialog, which) -> onNewQuizClick())
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getBinding().mainDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected MainActivityViewModel generateViewModel() {
        return ViewModelProviders.of(this, factory).get(MainActivityViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int getBindingVariableId() {
        return BR.viewModel;
    }

    @Override
    protected int setTitle() {
        return R.string.str_quiz;
    }

    public void openQuizActivity(Bundle bundle) {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtras(bundle);
        ActivityOptions activityOptions  = ActivityOptions.makeSceneTransitionAnimation(this,getBinding().startButton, getString(R.string.transition_button_quiz));
        startActivity(intent, activityOptions.toBundle());
//        finish();
    }

    public void openStaticActivity(){
        Intent intent = new Intent(this, StatisticActivity.class);
//        startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        startActivity(intent);
    }

    public void onContinueClick() {
        getViewModel().continueWithUnfinished();
    }

    public void onNewQuizClick() {
        getViewModel().startNewQuiz();
    }

    public Difficulty getCheckedDifficulty(int id) {
        Difficulty difficulty;
        switch (id) {
            case R.id.chip_difficulty_easy:
                difficulty = Difficulty.Easy;
                break;
            case R.id.chip_difficulty_medium:
                difficulty = Difficulty.Medium;
                break;
            case R.id.chip_difficulty_hard:
                difficulty = Difficulty.Hard;
                break;
            default:
                difficulty = Difficulty.Any;
        }
        return difficulty;
    }

    public Type getCheckedType(int id) {
        Type type;
        switch (id) {
            case R.id.chip_type_multiple:
                type = Type.MultipleChoice;
                break;
            case R.id.chip_type_boolean:
                type = Type.TrueFalse;
                break;
            default:
                type = Type.Any;
        }
        return type;
    }

    public int getCheckedAmount(int id) {
        switch (id) {
            case R.id.chip_amount_10:
                return 10;
            case R.id.chip_amount_15:
                return 15;
            case R.id.chip_amount_20:
                return 20;
            default:
                return 5;
        }
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - clickTime > 1000) {
            clickTime = currentTime;
            Toast.makeText(this, R.string.click_to_exit, Toast.LENGTH_SHORT).show();
        } else {
            Process.killProcess(Process.myPid());
        }
    }
}

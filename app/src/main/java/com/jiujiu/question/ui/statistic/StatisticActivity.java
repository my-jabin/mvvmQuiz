package com.jiujiu.question.ui.statistic;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Visibility;
import android.util.Log;
import android.view.MenuItem;

import com.jiujiu.question.BR;
import com.jiujiu.question.R;
import com.jiujiu.question.data.model.CategoryStatistic;
import com.jiujiu.question.data.model.DifficultyStatistic;
import com.jiujiu.question.data.model.TypeStatistic;
import com.jiujiu.question.databinding.ActivityStatisticBinding;
import com.jiujiu.question.ui.base.BaseActivity;
import com.jiujiu.question.ui.main.MainActivity;
import com.jiujiu.question.ui.statistic.category.CategoryStatisticFrag;
import com.jiujiu.question.ui.statistic.difficulty.DifficultyStatisticFrag;
import com.jiujiu.question.ui.statistic.type.TypeStatisticFrag;

import javax.inject.Inject;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

// todo: Since androix 1.0.0 alpha1 has some issue with dagger-android. we do not provide fragment inject here.
// should be changed when stable version comes
public class StatisticActivity extends BaseActivity<ActivityStatisticBinding, StatisticActivityViewModel> {

    private static final String TAG = "StatisticActivity";
    
    @Inject
    ViewModelProvider.Factory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowsAnimations();
        setupDrawerLayout();
        setupTabs();
    }

    private void setupWindowsAnimations() {
//        Explode explode = new Explode();
//        explode.setDuration(1000);
//        Fade  fade = new Fade();
//        fade.setDuration(500);
//        fade.setMode(Visibility.MODE_OUT);
//        getWindow().setEnterTransition(explode);
//        getWindow().setReturnTransition(fade);
    }

    private void setupTabs() {
        StatisticViewPagerAdapter adapter = new StatisticViewPagerAdapter(getSupportFragmentManager());
        getBinding().statisticViewpager.setAdapter(adapter);
        getBinding().statisticViewpager.setOffscreenPageLimit(2);
        getBinding().statisticTabs.setupWithViewPager(getBinding().statisticViewpager);
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getBinding().statisticDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerLayout() {
        getBinding().navigation.getMenu().findItem(R.id.navi_statistic_item).setChecked(true);
        getBinding().navigation.setNavigationItemSelectedListener(menuItem -> {
            getBinding().statisticDrawer.closeDrawers();
            switch (menuItem.getItemId()) {
                case R.id.navi_quiz_item:
                    openMainActivity();
                    break;
                case R.id.navi_statistic_item:
                    break;
                case R.id.navi_rate_item:
                    break;
            }
            return true;
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected StatisticActivityViewModel generateViewModel() {
        return ViewModelProviders.of(this, factory).get(StatisticActivityViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_statistic;
    }

    @Override
    public int getBindingVariableId() {
        return BR.viewModel;
    }

    @Override
    protected int setTitle() {
        return R.string.statistic;
    }


    private class StatisticViewPagerAdapter extends FragmentStatePagerAdapter {

        StatisticViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Log.d(TAG, "new fragment for category statistic");
//                    return StatisticBaseFragment.newInstance(StatisticBaseFragment.StatisticType.CategoryStatistic);
                    return new CategoryStatisticFrag();
                case 1:
                    Log.d(TAG, "new fragment for difficulty statistic");
//                    return StatisticBaseFragment.newInstance(StatisticBaseFragment.StatisticType.DifficultyStatistic);
                    return new DifficultyStatisticFrag();
                case 2:
                    Log.d(TAG, "new fragment for type statistic");
//                    return StatisticBaseFragment.newInstance(StatisticBaseFragment.StatisticType.TypeStatistic);
                    return new TypeStatisticFrag();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.str_category);
                case 1:
                    return getResources().getString(R.string.str_difficulty);
                case 2:
                    return getResources().getString(R.string.str_type);
                default:
                   throw new IllegalArgumentException("Pager adapter has only 3 pages.");
            }
        }
    }
}

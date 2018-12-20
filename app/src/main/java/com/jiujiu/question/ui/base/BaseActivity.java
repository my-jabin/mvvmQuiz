package com.jiujiu.question.ui.base;

import androidx.lifecycle.ViewModel;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.transition.Slide;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jiujiu.question.R;

import dagger.android.AndroidInjection;

public abstract class BaseActivity<T extends ViewDataBinding, V extends ViewModel> extends AppCompatActivity {

    private T mBinding;
    private V viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDenpendencyInjection();
        super.onCreate(savedInstanceState);
        performViewModel();
        performDataBinding();
        setupToolbar();
    }

    protected void setupToolbar() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(setTitle());
        }
    }

    protected int setTitle(){
        return R.string.app_name;
    }
    
    private void performViewModel() {
        viewModel = generateViewModel();
    }

    protected abstract V generateViewModel();

    @LayoutRes
    public abstract int getLayoutId();

    public V getViewModel() {
        return viewModel;
    }

    public void performDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        viewModel = viewModel == null ? getViewModel() : viewModel;
        mBinding.setVariable(getBindingVariableId(), viewModel);
        mBinding.executePendingBindings();
    }


    public void performDenpendencyInjection() {
        AndroidInjection.inject(this);
    }

    public abstract int getBindingVariableId();

    public T getBinding() {
        return mBinding;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}

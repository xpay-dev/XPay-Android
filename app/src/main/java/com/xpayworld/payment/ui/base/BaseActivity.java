package com.xpayworld.payment.ui.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.xpayworld.payment.PaymentApplication;
import com.xpayworld.payment.di.component.ActivityComponent;
import com.xpayworld.payment.di.component.DaggerActivityComponent;
import com.xpayworld.payment.di.module.ActivityModule;

public abstract class BaseActivity extends AppCompatActivity implements MvpView , BaseFragment.Callback {

    private ActivityComponent mActivityComponent;

    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((PaymentApplication) getApplication()).getAppComponent())
                .build();
        initViews();
        mContext = this;
    }

    public abstract void initViews();

    public abstract int getLayoutId();

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    @Override
    public void onError(int resId) {

    }

    @Override
    public void onError(String message) {

    }
    @Override
    public void showMessage(String message) {

    }
    @Override
    public void showMessage(int resId) {

    }
    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }


    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }
}

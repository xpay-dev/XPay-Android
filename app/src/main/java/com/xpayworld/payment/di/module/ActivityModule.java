package com.xpayworld.payment.di.module;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import com.xpayworld.payment.di.ActivityContext;
import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule {
    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }
    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }
}

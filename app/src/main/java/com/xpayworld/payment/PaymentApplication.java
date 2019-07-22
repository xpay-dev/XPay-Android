package com.xpayworld.payment;
import android.app.Application;
import com.xpayworld.payment.di.component.ApplicationComponent;
import com.xpayworld.payment.di.component.DaggerApplicationComponent;
import com.xpayworld.payment.di.module.ApplicationModule;


public class PaymentApplication extends Application {

    private ApplicationComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mAppComponent.inject(this);

    }
    public ApplicationComponent getAppComponent() {
        return mAppComponent;
    }
}

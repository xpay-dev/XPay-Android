package com.xpayworld.payment.di.component;

import android.app.Application;
import android.content.Context;

import com.xpayworld.payment.PaymentApplication;
import com.xpayworld.payment.di.ApplicationContext;
import com.xpayworld.payment.di.module.ActivityModule;
import com.xpayworld.payment.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(PaymentApplication app);

    @ApplicationContext
    Context context();

    Application application();
}

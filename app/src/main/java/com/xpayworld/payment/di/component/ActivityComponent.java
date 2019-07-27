package com.xpayworld.payment.di.component;

import com.xpayworld.payment.di.PerActivity;
import com.xpayworld.payment.di.module.ActivityModule;
import com.xpayworld.payment.di.module.ApplicationModule;
import com.xpayworld.payment.ui.activation.ActivationActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(ActivationActivity activity);
}
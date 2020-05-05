package com.xpayworld.payment

import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import com.xpayworld.payment.di.component.ApplicationComponent
import com.xpayworld.payment.di.component.DaggerApplicationComponent
import com.xpayworld.payment.di.module.ApplicationModule
import com.xpayworld.payment.util.RootUtil
import com.xpayworld.payment.util.isProbablyAnEmulator


class PaymentApplication : Application() {

    var appComponent: ApplicationComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        appComponent!!.inject(this)

    }
}

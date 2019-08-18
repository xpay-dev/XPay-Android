package com.xpayworld.payment.util

import android.app.Activity
import android.content.Context
import com.xpayworld.payment.ui.transaction.processTransaction.BBPosViewModelFactory

object InjectorUtil  {

    fun provideDialog(context: Context?): CustomDialog {
        return  CustomDialog(context!!)
    }

    fun provedeDeviceViewModelFactory(activity: Activity) : BBPosViewModelFactory {
        return BBPosViewModelFactory(activity)
    }
}
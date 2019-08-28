package com.xpayworld.payment.util

import android.Manifest
import android.app.Activity
import android.content.Context
import com.xpayworld.payment.network.activateApp.PosInfo
import com.xpayworld.payment.ui.activation.ActivationViewModelFactory
import com.xpayworld.payment.ui.transaction.enterAmount.EnterAmountViewModelFactory
import android.Manifest.permission
import android.Manifest.permission.READ_PHONE_STATE
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.telephony.TelephonyManager
import android.annotation.SuppressLint
import com.xpayworld.payment.ui.enterPin.EnterPinModelViewModelFactory
import com.xpayworld.payment.ui.enterPin.EnterPinViewModel


object InjectorUtil  {

    fun provideDialog(context: Context?): CustomDialog {
        return  CustomDialog(context!!)
    }

    fun provideEnterPinViewModelFactory(context: Context) :  EnterPinModelViewModelFactory{
        return  EnterPinModelViewModelFactory(context)
    }

    fun provideEnterAmountViewModelFactory(amount : String): EnterAmountViewModelFactory{
        return EnterAmountViewModelFactory(amount)
    }
    fun provideActivationViewModelFactor(context: Context): ActivationViewModelFactory{
        return  ActivationViewModelFactory(context)
    }


}
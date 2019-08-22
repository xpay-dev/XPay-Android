package com.xpayworld.payment.util

import android.app.Activity
import android.content.Context
import com.xpayworld.payment.ui.transaction.enterAmount.EnterAmountViewModelFactory

object InjectorUtil  {

    fun provideDialog(context: Context?): CustomDialog {
        return  CustomDialog(context!!)
    }

    fun provideEnterAmountViewModelFactory(amount : String): EnterAmountViewModelFactory{
        return EnterAmountViewModelFactory(amount)
    }

}
package com.xpayworld.payment.util

import android.app.Dialog
import android.content.Context
import android.view.Window

object InjectorUtil  {

    fun provideDialog(context: Context?): CustomDialog {
        return  CustomDialog(context!!)
    }
}
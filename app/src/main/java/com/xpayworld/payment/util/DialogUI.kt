package com.xpayworld.payment.util

import android.app.Dialog


interface DialogUI {

    fun onLoading(): Dialog
//    fun onSucess():Dialog
    fun onError():Dialog
    fun onDismiss()
}
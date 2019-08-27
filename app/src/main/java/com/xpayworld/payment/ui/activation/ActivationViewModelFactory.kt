package com.xpayworld.payment.ui.activation

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.xpayworld.payment.network.activateApp.PosInfo
import com.xpayworld.payment.ui.transaction.enterAmount.EnterAmountViewModel

class ActivationViewModelFactory(private val activity: Activity  ): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ActivationViewModel(activity) as T
    }}
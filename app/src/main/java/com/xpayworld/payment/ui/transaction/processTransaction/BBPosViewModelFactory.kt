package com.xpayworld.payment.ui.transaction.processTransaction

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BBPosViewModelFactory(private val activty: Activity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = BBPosViewModel(activty) as T
}
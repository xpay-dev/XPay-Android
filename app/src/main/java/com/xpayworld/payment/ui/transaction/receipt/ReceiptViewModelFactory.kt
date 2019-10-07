package com.xpayworld.payment.ui.transaction.receipt

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class  ReceiptViewModelFactory( private  val context: Context) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReceiptViewModel(context) as T
    }
}
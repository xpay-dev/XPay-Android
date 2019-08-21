package com.xpayworld.payment.ui.transaction.enterAmount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class  EnterAmountViewModelFactory(private val amount: String) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EnterAmountViewModel(amount) as T
    }
}
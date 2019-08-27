package com.xpayworld.payment.ui.transaction.processTransaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProcessTransactionViewModel : ViewModel(){

    val startAnimation : MutableLiveData<Boolean> = MutableLiveData()

    val onProcessTransaction : MutableLiveData<String> = MutableLiveData()
    
}
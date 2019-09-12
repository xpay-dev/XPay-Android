package com.xpayworld.payment.ui.transaction.processTransaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xpayworld.payment.network.transaction.EMVCard
import com.xpayworld.payment.util.BaseViewModel

class ProcessTransactionViewModel : BaseViewModel(){

    val startAnimation : MutableLiveData<Boolean> = MutableLiveData()

    val transactionApiResponse : MutableLiveData<String> = MutableLiveData()




    fun callTransactionAPI(card: EMVCard){



    }
}
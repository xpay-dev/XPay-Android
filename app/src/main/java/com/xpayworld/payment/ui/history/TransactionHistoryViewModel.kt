package com.xpayworld.payment.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xpayworld.payment.network.transLookUp.TransResponse
import com.xpayworld.payment.util.BaseViewModel

class TransactionHistoryViewModel  : BaseViewModel(){
    private  val txnNumber  = MutableLiveData<String>()
    private  val txnType = MutableLiveData<String>()
    private  val txnAmount = MutableLiveData<String>()
    private  val txnTimestamp = MutableLiveData<String>()

    fun bind(history : TransResponse){
        txnAmount.value = history.total
        txnNumber.value = history.transNumber
        txnType.value = history.transType
        txnTimestamp.value = history.timestamp
    }

    fun getTxnNumber(): MutableLiveData<String>{
        return txnNumber
    }

    fun getTxnAmount(): MutableLiveData<String>{
        return txnAmount
    }
    fun getTxnType(): MutableLiveData<String>{
        return txnType
    }
    fun getTxnTimestamp(): MutableLiveData<String>{
        return txnTimestamp
    }
}
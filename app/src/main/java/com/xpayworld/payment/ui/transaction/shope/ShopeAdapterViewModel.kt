package com.xpayworld.payment.ui.transaction.shope

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShopeAdapterViewModel : ViewModel(){
    private  val name  = MutableLiveData<String>()
    private  val amount = MutableLiveData<String>()
    private  val description = MutableLiveData<String>()
    private  val currency = MutableLiveData<String>()
    private  val id = MutableLiveData<Int>()


    fun bind(shope : Shoppe){
        name.value = shope.name
        amount.value = shope.amount
        description.value = shope.description
        currency.value = shope.currency
        id.value = shope.id
    }

    fun getName(): MutableLiveData<String> {
        return name
    }

    fun getAmount(): MutableLiveData<String> {
        return amount
    }

    fun getCurrency(): MutableLiveData<String> {
        return currency
    }


}
package com.xpayworld.payment.ui.enterPin

import android.view.View
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import io.reactivex.disposables.Disposable

class EnterPinModelView : ViewModel(){

    val clearClickListener = View.OnClickListener {onClickClear(it)}
    val numpadClickListener = View.OnClickListener {onClickNumpad(it)}
    val sumbitClickListener = View.OnClickListener {onClickSubmit(it)}
    val hideToolbar : MutableLiveData<Boolean> = MutableLiveData()
    val pinCode :  MutableLiveData<String> = MutableLiveData()

    private lateinit var subscription: Disposable

    init {
        hideToolbar.value = false
        pinCode.value = ""
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }


    private fun onClickNumpad(v: View?) {
        if (pinCode.value!!.length >=4 ) return
            pinCode.value += (v as Button).text.toString()
    }
    private fun onClickClear(v:View?){
       pinCode.value =  pinCode.value?.dropLast(1)
    }

    private fun onClickSubmit(v:View?){
        val direction = EnterPinFragmentDirections.actionEnterPinFragmentToTransactionFragment()
        v?.findNavController()?.navigate(direction)
        hideToolbar.value = true
    }
}



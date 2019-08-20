package com.xpayworld.payment.ui.transaction.enterAmount

import android.view.View
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.util.SharedPrefStorage
import com.xpayworld.payment.util.formattedAmount
import io.reactivex.disposables.Disposable




class EnterAmountViewModel : ViewModel(){

    val numpadClickListener = View.OnClickListener {onClickNumpad(it)}
    val displayAmount : MutableLiveData<String> = MutableLiveData()
    val transTypeSetResource : MutableLiveData<List<Int>> = MutableLiveData()
    val transTypeClickListener = View.OnClickListener {onClickTransType(it)}
    val clearClickListener = View.OnClickListener {onClickClear(it)}
    val okClickListener = View.OnClickListener {onClickOk(it)}

    private lateinit var subscription: Disposable
    var amountStr = ""

    init {
        displayAmount.value = "0.00"
        transTypeSetResource.value = listOf(R.drawable.tab_indenticator,R.drawable.tab_indenticator_clear)


    }

    override fun onCleared() {
        super.onCleared()
     //   subscription.dispose()
    }

    private fun onClickClear (v: View) {
        amountStr =  amountStr.dropLast(1)
        displayAmount.value = formattedAmount(amountStr)
    }

    private fun onClickOk (v:  View){
        if (amountStr.isEmpty()) return
        val direcetion = EnterAmountFragmentDirections.actionEnterAmountFragmentToProcessTranactionActivity()
        v.findNavController().navigate(direcetion)
 }

    private fun onClickNumpad(v : View){
        val len = amountStr.length
        if (len == 8) return
        if (len == 0 && (v as Button).text == "0") return
        amountStr += (v as Button).text

        displayAmount.value = formattedAmount(amountStr)
    }

    private fun onClickTransType (v: View) {
        val btn = v as Button
        if( btn.text == "Credit" )  {
            transTypeSetResource.value = listOf(R.drawable.tab_indenticator, R.drawable.tab_indenticator_clear)
        } else {
            transTypeSetResource.value = listOf(R.drawable.tab_indenticator_clear, R.drawable.tab_indenticator)
        }
    }
}


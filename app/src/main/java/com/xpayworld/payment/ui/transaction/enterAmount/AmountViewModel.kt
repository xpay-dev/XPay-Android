package com.xpayworld.payment.ui.transaction.enterAmount

import android.content.Context
import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.network.PosWsRequest
import com.xpayworld.payment.network.transaction.PaymentType
import com.xpayworld.payment.network.transaction.TransactionPurchase
import com.xpayworld.payment.util.*
import io.reactivex.disposables.Disposable


class AmountViewModel(context: Context) : BaseViewModel() {

    val displayAmount: MutableLiveData<String> = MutableLiveData()
    val okClickListener = View.OnClickListener { onClickOk(it) }
    val deviceError : MutableLiveData<Pair<String,String>> = MutableLiveData()
    //GG7C-BY7B-JF5A-HNN7
    val amountStr : MutableLiveData<String> = MutableLiveData()

    init {
        displayAmount.value = "0.00"
        posRequest = PosWsRequest(context)
    }

    private fun onClickOk(v: View) {
        if (!isDeviceAvailable(v.context)) {
            deviceError.value = Pair("No Device found","Please go to preference to pair device")
            return
        }
        if (amountStr.value!!.isEmpty()) return
        transaction.amount = ( amountStr.value!!.toInt()/100.0)
        if (isTransactionOffline){
            val direction = PayAmountFragmentDirections.actionPayAmountFragmentToProcessTransactionFragment(amountStr.value!!)
            v.findNavController().navigate(direction)
        } else {
            val direction = EnterAmountFragmentDirections.actionEnterAmountFragmentToProcessTranactionFragment(amountStr.value!!)
            v.findNavController().navigate(direction)
        }

    }


    private fun isDeviceAvailable(context: Context) : Boolean{
        return !SharedPrefStorage(context!!).isEmpty(WISE_PAD) || !SharedPrefStorage(context!!).isEmpty(WISE_POS)
    }
}


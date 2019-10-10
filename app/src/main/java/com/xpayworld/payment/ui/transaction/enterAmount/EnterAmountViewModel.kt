package com.xpayworld.payment.ui.transaction.enterAmount

import android.content.Context
import android.view.View
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.network.PosWsRequest
import com.xpayworld.payment.network.transaction.PaymentType
import com.xpayworld.payment.network.transaction.TransactionPurchase
import com.xpayworld.payment.util.*
import io.reactivex.disposables.Disposable


class EnterAmountViewModel( context: Context) : BaseViewModel() {

    val numpadClickListener = View.OnClickListener { onClickNumpad(it) }
    val displayAmount: MutableLiveData<String> = MutableLiveData()
    val transTypeSetResource: MutableLiveData<List<Int>> = MutableLiveData()
    val transTypeClickListener = View.OnClickListener { onClickTransType(it) }
    val clearClickListener = View.OnClickListener { onClickClear(it) }
    val okClickListener = View.OnClickListener { onClickOk(it) }
    val deviceError : MutableLiveData<Pair<String,String>> = MutableLiveData()

    var amountStr = ""

    init {
        displayAmount.value = "0.00"
        paymentType  = PaymentType.CREDIT(TransactionPurchase.Action.EMV)
        transTypeSetResource.value = listOf(R.drawable.tab_indenticator, R.drawable.tab_indenticator_clear)

        posRequest = PosWsRequest(context)
    }

    private fun onClickClear(v: View) {
        amountStr = amountStr.dropLast(1)
        displayAmount.value = formattedAmount(amountStr)
    }

    private fun onClickOk(v: View) {
        if (!isDeviceAvailable(v.context)) {
            deviceError.value = Pair("No Device found","Please go to preference to pair device")
            return
        }
        if (amountStr.isEmpty()) return
        transaction.amount = ( amountStr.toInt()/100.0)
        val direction = EnterAmountFragmentDirections.actionEnterAmountFragmentToProcessTranactionFragment(amountStr)
        v.findNavController().navigate(direction)
    }

    private fun onClickNumpad(v: View) {
        val len = amountStr.length
        if (len == 8) return
        if (len == 0 && (v as Button).text == "0") return
        amountStr += (v as Button).text

        displayAmount.value = formattedAmount(amountStr)
    }

    private fun onClickTransType(v: View) {
        val btn = v as Button
        if (btn.text == "Credit") {
            paymentType  = PaymentType.CREDIT(TransactionPurchase.Action.EMV)
            transTypeSetResource.value = listOf(R.drawable.tab_indenticator, R.drawable.tab_indenticator_clear)
        } else {
            paymentType  = PaymentType.DEBIT(PaymentType.DebitTransaction.SALE, TransactionPurchase.AccountType.SAVINGS)
            transTypeSetResource.value = listOf(R.drawable.tab_indenticator_clear, R.drawable.tab_indenticator)
        }
    }

    private fun isDeviceAvailable(context: Context) : Boolean{
        return !SharedPrefStorage(context!!).isEmpty(WISE_PAD) || !SharedPrefStorage(context!!).isEmpty(WISE_POS)
    }
}


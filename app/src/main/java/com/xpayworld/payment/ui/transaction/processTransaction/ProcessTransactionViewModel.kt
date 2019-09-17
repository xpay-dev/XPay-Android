package com.xpayworld.payment.ui.transaction.processTransaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xpayworld.payment.network.RetrofitClient
import com.xpayworld.payment.network.TransactionResponse
import com.xpayworld.payment.network.login.LoginApi
import com.xpayworld.payment.network.transaction.*
import com.xpayworld.payment.util.BaseViewModel
import com.xpayworld.payment.util.paymentType
import com.xpayworld.payment.util.transaction
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

import java.util.*

class ProcessTransactionViewModel : BaseViewModel() {


    val transactionApiResponse: MutableLiveData<String> = MutableLiveData()
    private var txnResponse: Single<Response<TransactionResponse>>? = null
    private lateinit var subscription: Disposable


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun callTransactionAPI() {

        val api = RetrofitClient().getRetrofit().create(TransactionApi::class.java)

        val txnPurchase = TransactionPurchase(transaction = transaction)

        when (val mPaymentType = paymentType) {
            is PaymentType.DEBIT -> {

            }
            is PaymentType.CREDIT -> {
                txnResponse = if (mPaymentType.action == TransactionPurchase.Action.SWIPE) {
                    api.creditSwipe(txnPurchase)
                } else {
                    api.creditEMV(txnPurchase)
                }
            }
        }

        subscription = txnResponse!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingVisibility.value = true }
                .doAfterTerminate { loadingVisibility.value = false }
                .subscribe { result ->


                    transactionApiResponse.value = "8A023030"
                }
    }
}
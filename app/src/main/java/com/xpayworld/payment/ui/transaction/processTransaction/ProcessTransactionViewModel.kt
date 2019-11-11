package com.xpayworld.payment.ui.transaction.processTransaction

import androidx.lifecycle.MutableLiveData
import com.xpayworld.payment.network.RetrofitClient
import com.xpayworld.payment.network.transaction.*
import com.xpayworld.payment.util.BaseViewModel
import com.xpayworld.payment.util.paymentType
import com.xpayworld.payment.util.transaction
import com.xpayworld.payment.util.transactionResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class ProcessTransactionViewModel : BaseViewModel() {


    val onlineAuthResult: MutableLiveData<String> = MutableLiveData()
    val transactionError : MutableLiveData<String> = MutableLiveData()
    private lateinit var subscription: Disposable


    fun callTransactionAPI() {
        var txnResponse: Single<Response<TransactionResult>>? = null
        val api = RetrofitClient().getRetrofit().create(TransactionApi::class.java)

        val txnPurchase = TransactionPurchase(transaction)

        when (val mPaymentType = paymentType) {
            is PaymentType.DEBIT -> {

            }
            is PaymentType.CREDIT -> {
                if (mPaymentType.action != TransactionPurchase.Action.SWIPE) {
                    txnResponse = api.creditEMV(TransactionRequest(txnPurchase))
                } else {
                    txnResponse = api.creditSwipe(TransactionRequest(txnPurchase))
                }
            }
        }

        subscription = txnResponse!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingVisibility.value = true }
                .doAfterTerminate { loadingVisibility.value = false }
                .subscribe({ result ->

                    if (!result.isSuccessful) {
                        networkError.value = "Network Error ${result.code()}"
                        return@subscribe
                    }
                    val body = if ( result.body()?.resultEmv != null) result.body()?.resultEmv else  result.body()?.resultSwipe
                    val hasError = body?.result?.errNumber != 0.0
                    if (hasError) {
                        requestError.value = body?.result
                        onlineAuthResult.value = "8A023035"
                    } else {
                        transactionResponse = body
//                        onlineAuthResult.value = "8A023030${body?.authNumber ?:""}"
                        onlineAuthResult.value = "8A023030"
                    }
                    subscription.dispose()
                }, {
                    println(it)
                    networkError.value = "Network Error"
                }
                )
    }
}
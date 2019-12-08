package com.xpayworld.payment.ui.transaction.processTransaction

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.xpayworld.payment.network.RetrofitClient
import com.xpayworld.payment.network.transaction.*
import com.xpayworld.payment.util.*
import com.xpayworld.sdk.XpayResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

import java.text.SimpleDateFormat
import java.util.*

class ProcessTransactionViewModel : BaseViewModel() {


    val onlineAuthResult: MutableLiveData<String> = MutableLiveData()
    val transactionError : MutableLiveData<String> = MutableLiveData()
    val offlineTransaction : MutableLiveData<String> = MutableLiveData()
    private lateinit var subscription: Disposable


    override fun onCleared() {
        super.onCleared()
       //subscription.dispose()
    }


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

    fun callOfflineTransction(context : Context){

        val trans = transaction
        val emv = trans.card!!

        val transRepository = com.xpayworld.payment.data.Transaction(
                amount = trans.amount,
                orderId = trans.orderId,
                timestamp =  System.currentTimeMillis() ,
                currency = trans.currency,
                currencyCode = trans.currencyCode,
                isOffline =  true,
                card = trans.card!!,
                device = 7
        )

        GlobalScope.launch {
            InjectorUtil.getTransactionRepository(context).createTransaction(transRepository)
        }

        var xPayResponse = XpayResponse()
        xPayResponse.cardNumber = emv.cardNumber
        xPayResponse.maskedCard = emv.cardXNumber
        xPayResponse.expiry = emv.expiryDate
        val gson = GsonBuilder().setPrettyPrinting().create()
        onlineAuthResult.value = "8A023030"
        offlineTransaction.value = gson.toJson(xPayResponse)

    }

    fun randomAlphaNumericString(desiredStrLength: Int): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..desiredStrLength)
                .map{ kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")
    }
}
package com.xpayworld.payment.ui.transaction.processTransaction

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.xpayworld.payment.data.EMVCardData
import com.xpayworld.payment.network.RetrofitClient
import com.xpayworld.payment.network.transaction.*
import com.xpayworld.payment.util.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

    fun callOfflineTransction(context : Context){

        val trans = transaction


        val emv = trans.emvCard!!

        val emvData = EMVCardData(
                cardholderName = emv.cardholderName,
                cardNumber = emv.cardNumber,
                ksn = emv.ksn,
                emvICCData = emv.emvICCData ,
                expiryDate =  emv.expiryDate,
                expiryMonth =  emv.expiryMonth,
                encTrack1 =  emv.encTrack1,
                encTrack2 = emv.encTrack2,
                posEntryMode = emv.posEntryMode,
                encTrack3 = emv.encTrack3,
                appReferredName = emv.appReferredName,
                epb = emv.epb,
                epbksn = emv.epbksn,
                cardXNumber = emv.cardXNumber,
                expiryYear = emv.expiryYear,
                trackEncoding = emv.trackEncoding,
                maskedPan = emv.maskedPan,
                serialNumber = emv.serialNumber,
                serviceCode = emv.serviceCode,
                appId = emv.appId)

        val transRepository = com.xpayworld.payment.data.Transaction(
                amount = transaction.amount,
                transNumber = "",
                transDate = "ddd",
                posEntry = emv.posEntryMode,
                currency = trans.currency,
                currencyCode = trans.currencyCode,
                isOffline =  true,
                emvCard = emvData
        )

        GlobalScope.launch {
          val data =    InjectorUtil.getTransactionRepository(context).getTransaction()

            data.forEach{ data ->

                println("DATA >>> ${data.emvCard.emvICCData}")

            }
        }

    }
}
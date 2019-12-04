package com.xpayworld.payment.ui.history

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.xpayworld.payment.network.PosWsResponse
import com.xpayworld.payment.network.RetrofitClient
import com.xpayworld.payment.network.TransactionResponse
import com.xpayworld.payment.network.transLookUp.*
import com.xpayworld.payment.network.transaction.*
import com.xpayworld.payment.util.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Timed
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*
import java.util.logging.Handler
import kotlin.concurrent.schedule
import kotlin.concurrent.timer

class TransactionHistoryViewModel(val context: Context): BaseViewModel(){

    val transResponse : MutableLiveData<List<TransactionResponse>> = MutableLiveData()
    val showError : MutableLiveData<Pair<String,String>> = MutableLiveData()
    val navigateToReceipt : MutableLiveData<Pair<List<TransactionResponse>, PosWsResponse>> = MutableLiveData()

    private lateinit var subscription: Disposable

    override fun onCleared() {
        super.onCleared()
    }


    fun callTransLookUpAPI(text: Observable<String>){

        val sharedPref = context.let { SharedPrefStorage(it) }

        val history = TransLookUp()
        history.posWsRequest =  posRequest
        history.mobileAppId = sharedPref.readMessage(MOBILE_APP_ID)
        history.accountId = sharedPref.readMessage(ACCOUNT_ID)
        history.mobileAppTransType = 1

        subscription =  text.subscribe{ text ->
            if (text.isEmpty()){
                history.searchCriteria = "1"
                history.searchUsing = 2
            } else {
                history.searchCriteria = text
                history.searchUsing = 1
            }
        }

        val api = RetrofitClient().getRetrofit().create(TransLookUpAPI::class.java)
        val historyReq = TransLookUpRequest(history)

        val apiCall = api.transLookUp(historyReq)

        subscription = text
                .switchMap { apiCall }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingVisibility.value = true }
                .doAfterTerminate {loadingVisibility.value = false}
                .subscribe(
                        { result ->
                        //    subscription.dispose()

                            loadingVisibility.value  = false
                            if (!result.isSuccessful) {
                                networkError.value = "Network Error ${result.code()}"
                            }
                            val hasError = result?.body()?.response?.posWsResponse?.errNumber != 0.0

                            if (hasError) {

                                requestError.value = result?.body()?.response?.posWsResponse?.message
                            } else {

                                val txns = result?.body()?.response?.transactions

                                if (txns?.count() != 0) {
                                    transResponse.value =  txns
                                } else {
                                    requestError.value = "No transaction available"
                                }
                            }
                        },
                        {
                            networkError.value = "Network Error"
                        }
                )
    }

    fun callTransactionAPI(txn : TransactionResponse){
        val sharedPref = context.let { SharedPrefStorage(it) }
        val history = TransLookUp()
        history.posWsRequest =  posRequest
        history.mobileAppId = sharedPref.readMessage(MOBILE_APP_ID)
        history.accountId = sharedPref.readMessage(ACCOUNT_ID)
        history.mobileAppTransType = 1
        history.searchCriteria = txn.transNumber
        history.searchUsing = 1


        val api = RetrofitClient().getRetrofit().create(TransLookUpAPI::class.java)
        val historyReq = TransLookUpRequest(history)

        subscription = api.transLookUp(historyReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingVisibility.value = true }
                .doAfterTerminate {loadingVisibility.value = false}
                .subscribe(
                        { result ->
                            subscription.dispose()
                            loadingVisibility.value  = false
                            if (!result.isSuccessful) {
                                networkError.value = "Network Error ${result.code()}"
                            }
                            val hasError = result?.body()?.response?.posWsResponse?.errNumber != 0.0

                            if (!hasError) {
                                val errorNumber = "REQUEST ERROR "+result.body()?.response?.posWsResponse?.errNumber!!
                                val errorMessage = result.body()?.response?.posWsResponse?.message!!
                                val message = Pair(errorNumber,errorMessage)
                                showError.value = message
                            } else {
                                val txns = result?.body()?.response?.transactions
                                val response = result.body()?.response?.posWsResponse!!
                                navigateToReceipt.value = Pair(txns!!,response)
                            }
                        },
                        {
                            networkError.value = "Network Error"
                        }
                )
        }

    fun callTransactionDelete(){
        InjectorUtil.getTransactionRepository(context).deleteAllTransaction()
    }

    fun callOfflineTransaction(){

        val trans = mutableListOf<TransactionResponse>()

           val txn = InjectorUtil.getTransactionRepository(context).getTransaction()
                txn.forEach {
                    val data = TransactionResponse()
                    data.transType = "Offline"
                    data.timestamp = it.timestamp
                    data.total = "${it.amount}"
                    data.currency = it.currency
                    data.transNumber = it.orderId
                    trans.add(data)
                }

        transResponse.value = trans
    }

    fun callTransactionAPI( callBack: ((Boolean) -> Unit)? = null) {
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
                       callBack?.invoke(false)
                        return@subscribe
                    }
                    callBack?.invoke(true)
                    subscription.dispose()
                }, {
                    callBack?.invoke(false)
                 })
    }

    fun callforBatchUpload(){

        val txnDao = InjectorUtil.getTransactionRepository(context)

        val dispatch = DispatchGroup()

            for (txn in txnDao.getTransaction()) {
                dispatch.enter()
                if (!txn.isSync){
                    txnDao.updateTransaction(true,txn.orderId)

                   val  trans = Transaction()
                    trans.posEntryMode = txn.posEntry
                    trans.emvCard?.appId = txn.emvCard.appId
                    trans.emvCard?.appReferredName = txn.emvCard.appReferredName
                    trans.emvCard?.cardNumber = txn.emvCard.cardNumber
                    trans.emvCard?.cardXNumber = txn.emvCard.cardXNumber
                    trans.emvCard?.cardholderName = txn.emvCard.cardholderName
                    trans.emvCard?.emvICCData = txn.emvCard.emvICCData
                    trans.emvCard?.encTrack1 = txn.emvCard.encTrack1
                    trans.emvCard?.encTrack2 = txn.emvCard.encTrack2
//                  trans.emvCard?.epb = txn.emvCard.epb
                    trans.emvCard?.epbksn = txn.emvCard.epbksn
                    trans.emvCard?.encTrack3 = txn.emvCard.encTrack3
                    trans.emvCard?.expiryDate = txn.emvCard.expiryDate
                    trans.emvCard?.expiryMonth = txn.emvCard.expiryMonth
                    trans.emvCard?.expiryYear = txn.emvCard.expiryYear
                    trans.emvCard?.ksn = txn.emvCard.ksn
                    trans.emvCard?.maskedPan = txn.emvCard.maskedPan
                    trans.emvCard?.serviceCode = txn.emvCard.serviceCode
                    trans.emvCard?.serialNumber = txn.emvCard.serialNumber
                    trans.orderId = txn.orderId
                    trans.isOffline = txn.isOffline
                    trans.amount = txn.amount
                    trans.currencyCode = txn.currencyCode
                    trans.currency = txn.currency

                    if (trans.posEntryMode == 0) {
                        trans.paymentType = PaymentType.CREDIT(TransactionPurchase.Action.SWIPE)
                    } else {
                        trans.paymentType = PaymentType.CREDIT(TransactionPurchase.Action.EMV)
                    }

                    trans.device = txn.device
                    trans.deviceModelVersion = txn.deviceModelVersion

                    transaction  = trans

                    callTransactionAPI(callBack = {isSuccess ->
                        if (isSuccess){
                            txnDao.deleteTranscation(trans.orderId)
                        }else {
                            txnDao.updateTransaction(false,trans.orderId)
                        }
                    })
                }
            }
            dispatch.notify {
                println("finish")
            }

    }
}




class DispatchGroup {
    private var count = 0
    private var runnable: (() -> Unit)? = null

    init {
        count = 0
    }

    @Synchronized
    fun enter() {
        count++
    }

    @Synchronized
    fun leave() {
        count--
        notifyGroup()
    }

    fun notify(r: () -> Unit) {
        runnable = r
        notifyGroup()
    }

    private fun notifyGroup() {
        if (count <= 0 && runnable != null) {
            runnable!!()
        }
    }
}
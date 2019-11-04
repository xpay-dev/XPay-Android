package com.xpayworld.payment.ui.history

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.xpayworld.payment.network.PosWsRequest
import com.xpayworld.payment.network.RetrofitClient
import com.xpayworld.payment.network.TransactionResponse
import com.xpayworld.payment.network.transLookUp.TransLookUp
import com.xpayworld.payment.network.transLookUp.TransLookUpAPI
import com.xpayworld.payment.network.transLookUp.TransLookUpRequest
import com.xpayworld.payment.network.transLookUp.TransResponse
import com.xpayworld.payment.util.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TransactionHistoryViewModel(val context: Context): BaseViewModel(){

    val transResponse : MutableLiveData<ArrayList<TransactionResponse>> = MutableLiveData()
    val navigateToReceipt : MutableLiveData<ArrayList<TransactionResponse>> = MutableLiveData()

    private lateinit var subscription: Disposable


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()

    }

    fun callTransLookUpAPI(text: Observable<String>){

        val sharedPref = context.let { SharedPrefStorage(it) }

        val history = TransLookUp()
        val pos = PosWsRequest(context)
        pos.rToken = "7swTXuxaMLFIle048m6ejqJwaKxMOo8HIgI9I%2f6%2be%2f6Ajcf6dJ%2bdxKTVj7ntzrjV"
        pos.activationKey = "GFI23S6AW52TEJ4B"

        history.posWsRequest =  pos
        history.mobileAppId =  "8"
        history.accountId = "9"
//        history.mobileAppId = sharedPref.readMessage(MOBILE_APP_ID)
//        history.accountId = sharedPref.readMessage(ACCOUNT_ID)
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

        subscription = text.distinctUntilChanged()
                .switchMap { apiCall }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingVisibility.value = true }
                .doAfterTerminate {loadingVisibility.value = false}
                .subscribe(
                        { result ->
                            loadingVisibility.value  = false
                            if (!result.isSuccessful) {
                                networkError.value = "Network Error ${result.code()}"
                            }
                            val hasError = result?.body()?.response?.posWsResponse?.errNumber != 0.0

                            if (hasError) {

                                requestError.value = result?.body()?.response?.posWsResponse?.message
                            } else {

                                val txns = result?.body()?.response?.transactions as ArrayList<TransactionResponse>

                                if (txns.count() != 0) {
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

        val history = TransLookUp()
        val pos = PosWsRequest(context)
        pos.rToken = "7swTXuxaMLFIle048m6ejqJwaKxMOo8HIgI9I%2f6%2be%2f6Ajcf6dJ%2bdxKTVj7ntzrjV"
        pos.activationKey = "GFI23S6AW52TEJ4B"

        history.posWsRequest =  pos
        history.mobileAppId =  "8"
        history.accountId = "9"
//        history.mobileAppId = sharedPref.readMessage(MOBILE_APP_ID)
//        history.accountId = sharedPref.readMessage(ACCOUNT_ID)
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
                            loadingVisibility.value  = false
                            if (!result.isSuccessful) {
                                networkError.value = "Network Error ${result.code()}"
                            }
                            val hasError = result?.body()?.response?.posWsResponse?.errNumber != 0.0

                            if (hasError) {
                                val txns = result?.body()?.response?.transactions as ArrayList<TransactionResponse>
                                navigateToReceipt.value = txns
                            }
                        },
                        {
                            networkError.value = "Network Error"
                        }
                )
    }
}
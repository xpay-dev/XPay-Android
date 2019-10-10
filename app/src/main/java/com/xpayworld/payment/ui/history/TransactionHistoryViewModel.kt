package com.xpayworld.payment.ui.history

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.xpayworld.payment.network.RetrofitClient
import com.xpayworld.payment.network.activateApp.ActivationApi
import com.xpayworld.payment.network.transLookUp.TransLookUp
import com.xpayworld.payment.network.transLookUp.TransLookUpAPI
import com.xpayworld.payment.network.transLookUp.TransLookUpRequest
import com.xpayworld.payment.network.transLookUp.TransResponse
import com.xpayworld.payment.util.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TransactionHistoryViewModel(val context: Context): BaseViewModel(){

    val transResponse : MutableLiveData<TransResponse> = MutableLiveData()

    private lateinit var subscription: Disposable


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()

    }
    fun callTransLookUp(){
        val sharedPref = context.let { SharedPrefStorage(it) }

        val history = TransLookUp()
        history.posWsRequest =  posRequest
        history.mobileAppId = sharedPref.readMessage(MOBILE_APP_ID)
        history.accountId = sharedPref.readMessage(ACCOUNT_ID)
        history.mobileAppTransType = 1
        history.searchCriteria = ""
        history.searchUsing = 2

        val api = RetrofitClient().getRetrofit().create(TransLookUpAPI::class.java)

        val historyReq = TransLookUpRequest(history)

        subscription = api.transLookUp(historyReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingVisibility.value = true }
                .doAfterTerminate {loadingVisibility.value = false}
                .subscribe(
                        { result ->
                            if (!result.isSuccessful) {
//                                networkError.value = "Network Error ${result.code()}"
//                                pinCode.value = ""
//                                return@subscribe
                            }
                            val hasError = result?.body()?.result?.errNumber != 0.0

                            if (hasError) {
//                                loadingVisibility.value = false
//                                pinCode.value = ""
//                                requestError.value = hasError

                            } else {

//                                val response = result?.body()?.result
//                                val sharedPref = context.let { SharedPrefStorage(it) }
//                                posRequest!!.rToken  =  response!!.rToken!!

                            }
                        },
                        {
//                            loadingVisibility.value = false
//                            pinCode.value = ""
//                            networkError.value = "Network Error"
                        }
                )
        }
}
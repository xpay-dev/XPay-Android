package com.xpayworld.payment.ui.activation

import android.os.Handler
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.xpayworld.payment.network.PosWsRequest
import com.xpayworld.payment.network.PosWsResponse
import com.xpayworld.payment.network.RetrofitClient
import com.xpayworld.payment.network.activateApp.Activation
import com.xpayworld.payment.network.activateApp.ActivationApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*


class ActivationViewModel : ViewModel() {

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val activateClickListener = View.OnClickListener {onClickActivate()}

    val  networkError : MutableLiveData<Throwable> = MutableLiveData()
    val  apiError : MutableLiveData<Boolean> = MutableLiveData()

    private lateinit var subscription: Disposable


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun onClickActivate() {
        val api = RetrofitClient().getRetrofit().create(ActivationApi::class.java)
        val pos = PosWsRequest()
        pos.ActivationKey = ""
        pos.GPSLat = "0.0"
        pos.GPSLong = "0.0"
        pos.RToken = ""
        pos.SystemMode = "Live"

        val activate = Activation()
        activate.IMEI = ""
        activate.IP = ""
        activate.Manufacturer = ""
        activate.Model = ""
        activate.POSWSRequest = pos

        subscription = api.activation(activate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingVisibility.value = true }
                .doAfterTerminate { loadingVisibility.value = false }

                .subscribe(
                        { result ->
                            val hasError = result?.body()?.ErrNumber !=0
                            if (hasError) {
                                apiError.value = hasError
                                Handler().postDelayed({
                                    apiError.value = !hasError
                                }, 1000)
                            }
                        },
                        {
                            networkError.value = it
                        })
    }
}
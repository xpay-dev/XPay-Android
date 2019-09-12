package com.xpayworld.payment.ui.activation

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.telephony.TelephonyManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xpayworld.payment.network.PosWsRequest
import com.xpayworld.payment.network.RetrofitClient
import com.xpayworld.payment.network.activateApp.Activation
import com.xpayworld.payment.network.activateApp.ActivationApi
import com.xpayworld.payment.network.activateApp.PosInfo
import com.xpayworld.payment.util.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class ActivationViewModel(private val context: Context) : BaseViewModel() {

    val requestError: MutableLiveData<Boolean> = MutableLiveData()
    //    val activateClickListener = View.OnClickListener { onClickActivate(it)}
    val navigateToEnterPin: MutableLiveData<String> = MutableLiveData()

    private lateinit var subscription: Disposable


    init {
        toolbarVisibility.value = false
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()

    }

    fun callActivationAPI(code: String) {

        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val pos = PosWsRequest()
        pos.activationKey = code
        pos.gpsLat = "0.0"
        pos.gpsLong = "0.0"
        pos.rToken = "asdasda"
        pos.systemMode = "Live"


        val activate = Activation()
        activate.imei = "asdasda"
        activate.ip = "192.168.1.1"
        activate.manufacturer = "Android"
        activate.model = "adas"
        activate.posWsRequest = pos
        activate.platform="android"


        val posInfo = PosInfo()
        posInfo.request = activate

        val api = RetrofitClient().getRetrofit().create(ActivationApi::class.java)

        subscription = api.activation(posInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingVisibility.value = true }
                .doAfterTerminate { loadingVisibility.value = false }
                .subscribe(
                        { result ->
                            if (!result.isSuccessful) {
                                networkError.value
                                return@subscribe
                            }

                            val hasError = result?.body()?.result?.errNumber != 0.0

                            if (hasError) {
                                requestError.value = hasError
                                Handler().postDelayed({
                                    requestError.value = !hasError
                                }, 3000)

                            } else {
                                navigateToEnterPin.value = posInfo.request.posWsRequest?.activationKey.toString()
                            }
                        },
                        {
                            networkError.value = "Request Error"
                        }
                )}
}
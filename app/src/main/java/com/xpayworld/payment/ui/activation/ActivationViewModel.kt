package com.xpayworld.payment.ui.activation

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.telephony.TelephonyManager
import android.view.View
import android.view.View.OnClickListener
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.xpayworld.payment.network.PosWsRequest
import com.xpayworld.payment.network.RetrofitClient
import com.xpayworld.payment.network.activateApp.Activation
import com.xpayworld.payment.network.activateApp.ActivationApi
import com.xpayworld.payment.network.activateApp.PosInfo
import com.xpayworld.payment.util.SharedPrefStorage
import com.xpayworld.payment.util.getDeviceIMEI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_enter_amount.*
import kotlin.coroutines.coroutineContext


class ActivationViewModel(activity: Activity) : ViewModel() {

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val networkError: MutableLiveData<Throwable> = MutableLiveData()
    val apiError: MutableLiveData<Boolean> = MutableLiveData()

    //    val activateClickListener = View.OnClickListener { onClickActivate(it)}
    val toolbarVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val navigateToEnterPin: MutableLiveData<String> = MutableLiveData()

    private lateinit var subscription: Disposable

    private val activity = activity


    init {
        toolbarVisibility.value = false
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()

    }

    fun processActivation(code: String) {

        val tm = activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
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

                            val hasError = result?.body()?.result?.errNumber != "00"

                            if (hasError) {
                                apiError.value = hasError
                                Handler().postDelayed({
                                    apiError.value = !hasError
                                }, 3000)

                            } else {
                                navigateToEnterPin.value = posInfo.request.posWsRequest?.activationKey.toString()
                            }
                        },
                        {
                            networkError.value = it
                        }
                )}
}
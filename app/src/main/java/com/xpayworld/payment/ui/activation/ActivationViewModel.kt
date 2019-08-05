package com.xpayworld.payment.ui.activation

import android.os.Handler
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.xpayworld.payment.network.PosWsRequest
import com.xpayworld.payment.network.RetrofitClient
import com.xpayworld.payment.network.activateApp.Activation
import com.xpayworld.payment.network.activateApp.ActivationApi
import com.xpayworld.payment.util.SharedPrefStorage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class ActivationViewModel : ViewModel() {

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    val activateClickListener = View.OnClickListener { onClickActivate(it)}
    val hideToolbar : MutableLiveData<Boolean> = MutableLiveData()
    val  networkError : MutableLiveData<Throwable> = MutableLiveData()
    val  apiError : MutableLiveData<Boolean> = MutableLiveData()

    private lateinit var subscription: Disposable


    init {
        hideToolbar.value = false
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()

    }

    private fun onClickActivate(v : View) {
        val api = RetrofitClient().getRetrofit().create(ActivationApi::class.java)
        val pos = PosWsRequest()
        pos.activationKey = "123sda123asdasdd"
        pos.gpsLat = "0.0"
        pos.gpsLong = "0.0"
        pos.rToken = ""
        pos.systemMode = "Live"




        val activate = Activation()
        activate.imei = ""
        activate.ip = ""
        activate.manufacturer = ""
        activate.model = ""
        activate.posWsRequest = pos

        subscription = api.activation(activate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingVisibility.value = true }
                .doAfterTerminate { loadingVisibility.value = false }
                .subscribe(
                        { result ->

                            val hasError = result?.body()?.errNumber == "00"

                            if (hasError) {

                                apiError.value = hasError
                                Handler().postDelayed({
                                    apiError.value = !hasError
                                }, 3500)

                            } else {

                                val sharedPref = v.context?.let { it -> SharedPrefStorage(it) }
                                sharedPref!!.writeMessage("activationKey",pos.activationKey.toString())

                                val direction = ActivationFragmentDirections.actionActiviationFragmentToEnterPinFragment()
                                v.findNavController().navigate(direction)
                            }
                        },
                        {
                                    networkError.value = it
                        })}
}
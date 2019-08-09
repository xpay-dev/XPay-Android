package com.xpayworld.payment.ui.enterPin

import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.xpayworld.payment.network.PosWsRequest
import com.xpayworld.payment.network.RetrofitClient
import com.xpayworld.payment.network.login.Login
import com.xpayworld.payment.network.login.LoginApi
import com.xpayworld.payment.ui.activation.ActivationFragmentDirections
import com.xpayworld.payment.util.SharedPrefStorage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class EnterPinModelView : ViewModel() {

    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val networkError: MutableLiveData<String> = MutableLiveData()
    val apiError: MutableLiveData<Boolean> = MutableLiveData()

    val clearClickListener = View.OnClickListener { onClickClear(it) }
    val numpadClickListener = View.OnClickListener { onClickNumpad(it) }
    val sumbitClickListener = View.OnClickListener { onClickSubmit(it) }
    val toolbarVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val pinCode: MutableLiveData<String> = MutableLiveData()

    private lateinit var subscription: Disposable

    init {
        toolbarVisibility.value = false
        pinCode.value = ""
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun onClickNumpad(v: View?) {
        if (pinCode.value!!.length >= 4) return
        pinCode.value += (v as Button).text.toString()
    }

    private fun onClickClear(v: View?) {
        pinCode.value = pinCode.value?.dropLast(1)
    }

    private fun onClickSubmit(v: View) {
        val api = RetrofitClient().getRetrofit().create(LoginApi::class.java)

        val pos = PosWsRequest()
        pos.activationKey = "123sda123asdasdd"
        pos.gpsLat = "0.0"
        pos.gpsLong = "0.0"
        pos.rToken = ""
        pos.systemMode = "Live"
        val login = Login()
        login.appVersion = ""
        login.password = ""
        login.pin = ""
        login.userName = ""
        login.posWsRequest = pos

        subscription = api.login(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingVisibility.value = true }
                .doAfterTerminate { loadingVisibility.value = false }

                .subscribe(
                        { result ->
                            if (!result.isSuccessful) {
                                networkError.value = "Server Error ${result.code()}"
                                pinCode.value = ""
                                return@subscribe
                            }

                            val hasError = result?.body()?.errNumber == "00"
                            if (hasError) {
                                apiError.value = hasError
                            } else {
                                val direction = EnterPinFragmentDirections.actionEnterPinFragmentToTransactionFragment()
                               v.findNavController().navigate(direction)
                            }
                        },
                         {
                    networkError.value = "Network Error"
                }
                )

    }

}



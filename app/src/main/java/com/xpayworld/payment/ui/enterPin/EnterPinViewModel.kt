package com.xpayworld.payment.ui.enterPin

import android.content.Context
import android.view.ContextMenu
import android.view.View
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.xpayworld.payment.network.PosWsRequest
import com.xpayworld.payment.network.RetrofitClient
import com.xpayworld.payment.network.login.Login
import com.xpayworld.payment.network.login.LoginApi
import com.xpayworld.payment.network.login.LoginRequest
import com.xpayworld.payment.util.BaseViewModel
import com.xpayworld.payment.util.SharedPrefStorage
import com.xpayworld.payment.util.posRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlin.math.log

class EnterPinViewModel( private val context: Context) : BaseViewModel() {


    val apiError: MutableLiveData<Boolean> = MutableLiveData()

    val clearClickListener = View.OnClickListener { onClickClear(it) }
    val numpadClickListener = View.OnClickListener { onClickNumpad(it) }

    val pinCode: MutableLiveData<String> = MutableLiveData()
    val navigateToEnterAmount : MutableLiveData<String> = MutableLiveData()

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

     fun callEnterPinAPI() {
        val api = RetrofitClient().getRetrofit().create(LoginApi::class.java)
        val login = Login(context)
        login.appVersion = "1"
        login.password = "M_HPay12"
        login.pin = pinCode.value!!
        login.userName = "M_HPay"

        val request = LoginRequest()
        request.request = login

        subscription = api.login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingVisibility.value = true }
                .doAfterTerminate { loadingVisibility.value = false }
                .subscribe(
                        { result ->

                            if (!result.isSuccessful) {
                                networkError.value = "Network Error ${result.code()}"
                                pinCode.value = ""
                                return@subscribe
                            }

                            val hasError = result?.body()?.result?.errNumber != 0.0
                            if (hasError) {
                                pinCode.value = ""
                                apiError.value = hasError
                            }

                            else {
                                val response =   result?.body()?.result
                                val sharedPref = context.let { SharedPrefStorage(it!!) }
                                response?.rToken?.let { sharedPref.writeMessage("rtoken", it) }
                                toolbarVisibility.value = true
                                navigateToEnterAmount.value = ""
                            }
                        },
                        {
                            println(it)
                            pinCode.value = ""
                            networkError.value = "Network Error"
                        }
                )
    }
}



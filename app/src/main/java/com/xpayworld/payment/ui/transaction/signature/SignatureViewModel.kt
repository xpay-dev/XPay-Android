package com.xpayworld.payment.ui.transaction.signature

import com.xpayworld.payment.network.RetrofitClient
import com.xpayworld.payment.network.signature.Signature
import com.xpayworld.payment.network.signature.SignatureApi
import com.xpayworld.payment.network.signature.SignatureRequest
import com.xpayworld.payment.network.updateApp.UpdateAppApi
import com.xpayworld.payment.util.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SignatureViewModel  : BaseViewModel() {

    private lateinit var subscription: Disposable

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun callSignatureAPI(){

        val  sign = Signature()
        sign.imageStr = ""
        sign.imageLenStr = ""

        val signRequest = SignatureRequest()
        signRequest.request = sign

        val api = RetrofitClient().getRetrofit().create(SignatureApi::class.java)
        subscription = api.signature(signRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingVisibility.value = true }
                .doAfterTerminate { loadingVisibility.value = false }
                .subscribe({ result ->


                })
    }
}
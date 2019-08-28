package com.xpayworld.payment.network.login

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.xpayworld.payment.network.PosWsRequest
import com.xpayworld.payment.util.SharedPrefStorage

class Login (context : Context) {
    @SerializedName("AppVersion")
    var appVersion = ""

    @SerializedName("Pin")
    var pin = ""

    @SerializedName("Password")
    var password = ""

    @SerializedName("Username")
     var userName = ""

    @SerializedName("POSWSRequest")
     var posWsRequest: PosWsRequest? = null

    init {
        val sharedPref = context.let { SharedPrefStorage(it!!) }
        val posReq = PosWsRequest()
        posReq.activationKey =   sharedPref.readMessage("activationKey")

        posWsRequest  = posReq
    }
}
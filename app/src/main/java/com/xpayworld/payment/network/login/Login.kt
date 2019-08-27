package com.xpayworld.payment.network.login

import com.google.gson.annotations.SerializedName
import com.xpayworld.payment.network.PosWsRequest

class Login {
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
}
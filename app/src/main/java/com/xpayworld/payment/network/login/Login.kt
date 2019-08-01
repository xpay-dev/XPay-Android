package com.xpayworld.payment.network.login

import com.google.gson.annotations.SerializedName
import com.xpayworld.payment.network.PosWsRequest

class Login {
    @SerializedName("AppVersion")
    private var AppVersion = ""

    @SerializedName("Pin")
    private var Pin = ""
    @SerializedName("Password")
    private var Password = ""

    @SerializedName("Username")
    private var UserName = ""


    @SerializedName("posWsRequest")
    private var POSWSRequest: PosWsRequest? = null
}
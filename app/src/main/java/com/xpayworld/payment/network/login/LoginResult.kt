package com.xpayworld.payment.network.login

import com.google.gson.annotations.SerializedName
import com.xpayworld.payment.network.PosWsResponse

class LoginResult {
    @SerializedName("LoginResult")
    var result = PosWsResponse()
}
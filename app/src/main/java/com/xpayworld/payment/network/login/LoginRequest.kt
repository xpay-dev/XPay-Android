package com.xpayworld.payment.network.login

import com.google.gson.annotations.SerializedName

class LoginRequest {
    @SerializedName("request")
    var request =  Login()
}
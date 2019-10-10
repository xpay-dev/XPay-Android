package com.xpayworld.payment.network

import com.google.gson.annotations.SerializedName
import com.xpayworld.payment.util.RTOKEN

class PosWsRequest {

    @SerializedName("ActivationKey")
     var activationKey: String? = null

    @SerializedName("GPSLat")
     var gpsLat: String? = "0.0"

    @SerializedName("GPSLong")
     var gpsLong: String? = "0.0"

    @SerializedName("RToken")
     var rToken: String? = ""

    @SerializedName("SystemMode")
     var systemMode: String? = "Live"


}
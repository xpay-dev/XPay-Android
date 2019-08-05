package com.xpayworld.payment.network

import com.google.gson.annotations.SerializedName

class PosWsRequest {

    @SerializedName("ActivationKey")
     var activationKey: String? = null

    @SerializedName("GPSLat")
     var gpsLat: String? = null

    @SerializedName("GPSLong")
     var gpsLong: String? = null

    @SerializedName("RToken")
     var rToken: String? = null

    @SerializedName("SystemMode")
     var systemMode: String? = null

}
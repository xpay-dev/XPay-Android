package com.xpayworld.payment.network

import com.google.gson.annotations.SerializedName

class PosWsRequest {

    @SerializedName("ActivationKey")
     var ActivationKey: String? = null

    @SerializedName("GPSLat")
     var GPSLat: String? = null

    @SerializedName("GPSLong")
     var GPSLong: String? = null

    @SerializedName("RToken")
     var RToken: String? = null

    @SerializedName("SystemMode")
     var SystemMode: String? = null

}
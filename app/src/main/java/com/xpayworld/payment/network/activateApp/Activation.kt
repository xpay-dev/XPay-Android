package com.xpayworld.payment.network.activateApp

import com.google.gson.annotations.SerializedName
import com.xpayworld.payment.network.PosWsRequest

class Activation{

    @SerializedName("IMEI")
     var imei: String? = null

    @SerializedName("IP")
     var ip: String? = null

    @SerializedName("Manufacturer")
     var manufacturer: String? = null

    @SerializedName("Model")
     var model: String? = null

    @SerializedName("OS")
     var os: String? = null

    @SerializedName("PosWsRequest")
     var posWsRequest: PosWsRequest? = null

    @SerializedName("PhoneNumber")
     var phoneNumber: String? = null

    @SerializedName("Platform")
     var platform: String? = null
    @SerializedName("PosType")
     var postType: String? = null
}
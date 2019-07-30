package com.xpayworld.payment.network.activateApp

import com.google.gson.annotations.SerializedName
import com.xpayworld.payment.network.PosWsRequest

class Activation{

    @SerializedName("IMEI")
     var IMEI: String? = null

    @SerializedName("IP")
     var IP: String? = null

    @SerializedName("Manufacturer")
     var Manufacturer: String? = null

    @SerializedName("Model")
     var Model: String? = null

    @SerializedName("OS")
     var OS: String? = null

    @SerializedName("POSWSRequest")
     var POSWSRequest: PosWsRequest? = null

    @SerializedName("PhoneNumber")
     var PhoneNumber: String? = null

    @SerializedName("Platform")
     var Platform: String? = null
    @SerializedName("PosType")
     var PosType: String? = null
}
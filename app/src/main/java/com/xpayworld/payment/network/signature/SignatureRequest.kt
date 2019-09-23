package com.xpayworld.payment.network.signature

import com.google.gson.annotations.SerializedName
import com.xpayworld.payment.network.PosWsRequest
import com.xpayworld.payment.util.posRequest

class SignatureRequest(
    img: String = "" ,
    imgLen: String = "" ,
    mobileType: Int = 0,
    transNumber: Int = 0){

        @SerializedName("Image")
        var imageStr : String? = null
        @SerializedName("ImageLen")
        var imageLenStr : String? = null
        @SerializedName("MobileAppTransType")
        var mobileAppTransTypeInt : Int? = 0
        @SerializedName("TransNumber")
        var transNumberInt : Int? = 0
        @SerializedName("POSWSRequest")
        var posWsRequest : PosWsRequest? = null

        init {
            imageStr = img
            imageLenStr = imgLen
            mobileAppTransTypeInt = mobileType
            transNumberInt = transNumber
            posWsRequest = posRequest
        }
}
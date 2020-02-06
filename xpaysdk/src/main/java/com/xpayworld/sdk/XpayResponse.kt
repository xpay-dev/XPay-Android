package com.xpayworld.sdk

import com.google.gson.annotations.SerializedName

class  XpayResponse {
    @SerializedName("response")
    var response  = ""

    @SerializedName("masked_card")
    var maskedCard = ""

    @SerializedName("card_expiry")
    var expiry= ""

    @SerializedName("response_message")
    var responseMsg = ""

    @SerializedName("order_id")
    var orderId = ""
}
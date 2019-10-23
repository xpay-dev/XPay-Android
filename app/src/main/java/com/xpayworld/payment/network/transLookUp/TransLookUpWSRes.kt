package com.xpayworld.payment.network.transLookUp

import com.google.gson.annotations.SerializedName
import com.xpayworld.payment.network.PosWsRequest
import com.xpayworld.payment.network.PosWsResponse


class TransLookUpWSRes {

    @SerializedName("POSWSResponse")
    val posWsResponse: PosWsResponse? = null

    @SerializedName("Transactions")
    val transactions: List<TransResponse>? = null
}

class TransLookUpResponse {

    @SerializedName("TransLookUpResult")
    var response : TransLookUpWSRes? = null
}
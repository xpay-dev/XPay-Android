package com.xpayworld.payment.network.transLookUp

import com.google.gson.annotations.SerializedName
import com.xpayworld.payment.network.PosWsRequest


class TransLookUpWSRes {

    @SerializedName("POSWSResponse")
    val posWsRequest: PosWsRequest? = null

    @SerializedName("Transactions")
    val transactions: List<TransResponse>? = null
}

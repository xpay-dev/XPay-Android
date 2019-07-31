package com.xpayworld.payment.network

import com.google.gson.annotations.SerializedName


class PosWsResponse {

    @SerializedName("AccountId")
     val AccountId: String? = null

    @SerializedName("ErrNumber")
     val ErrNumber: Int? = null

    @SerializedName("Message")
     val Message: String? = null

    @SerializedName("MobileAppId")
     val MobileAppId: String? = null


    @SerializedName("RToken")
     val RToken: String? = null

    @SerializedName("SequenceNumber")
     val SequenceNumber: String? = null

    @SerializedName("Status")
     val Status: String? = null

    @SerializedName("UpdatePending")
     val UpdatePending: Boolean = false

}
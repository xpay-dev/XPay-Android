package com.xpayworld.payment.network.signature

import com.xpayworld.payment.network.ApiConstants
import retrofit2.http.Headers
import retrofit2.http.POST

interface  Signature{


    @Headers(
            ApiConstants.Charset,
            ApiConstants.Content)
    @POST(ApiConstants.TransSign)
    fun sign()
}
package com.xpayworld.sdk

import android.content.Intent

interface  ITransCall{
    fun callTransaction(params : XpayRequest): Intent
    fun callHistory()
    fun callPreference()
    fun callEnterPin()
    fun callActivation()
}
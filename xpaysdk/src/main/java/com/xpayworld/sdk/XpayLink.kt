package com.xpayworld.sdk

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder

class  XpayLink : ITransCall{
    override fun callHistory() {

    }

    override fun callPreference() {

    }

    override fun callEnterPin() {
    }

    override fun callActivation() {
    }

    //
    override fun callTransaction(context: Context, params: XpayRequest): Intent {
        val i = Intent(XPAY_LINK)
        val b = Bundle()

        val gson = GsonBuilder().setPrettyPrinting().create()
        val gsonStr = gson.toJson(params)
        b.putString(XPAY_REQUEST,gsonStr)
        i.putExtras(b)
        return  i
    }

    init {
        INSTANCE = this
    }

    companion object{
        var INSTANCE : XpayLink
        init {
            INSTANCE = XpayLink()
        }
    }
}
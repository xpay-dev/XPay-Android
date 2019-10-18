package com.xpayworld.sdk

import android.content.Intent
import android.os.Bundle

class  XpayLink : ITransCall{
    override fun callHistory() {

    }

    override fun callPreference() {

    }

    override fun callEnterPin() {
    }

    override fun callActivation() {
    }


    override fun callTransaction(params: XpayRequest): Intent {
        val i = Intent(XPAY_LINK)
        val b = Bundle()

        b.putString(XPAY_REQUEST,"")
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
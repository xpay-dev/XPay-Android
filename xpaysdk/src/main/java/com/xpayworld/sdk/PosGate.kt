package com.xpayworld.sdk

import java.io.PipedOutputStream

class  PosGate public constructor(){

    var merchantName = ""
    var txnType = ""
    var txnId = ""
    var cardCaptureMethod = ""
    var amountPurchase = ""
    var staffId = ""
    var appPackageName = ""

    init {
        INSTANCE = this
    }

    companion object{
        var INSTANCE : PosGate
        init {
           INSTANCE = PosGate()
        }
    }
}

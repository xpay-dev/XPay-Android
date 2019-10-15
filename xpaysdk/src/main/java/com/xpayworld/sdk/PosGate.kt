package com.xpayworld.sdk

import java.io.PipedOutputStream

class  PosGate private constructor(){

    var merchantName = ""
    var txnType = ""
    var txnId = ""
    var cardCaptureMethod = ""
    var amountPurchase = ""
    var staffId = ""

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

package com.xpayworld.payment.network.transaction

import com.xpayworld.payment.data.CardData

class Transaction {
    var amount = 0.0
    var tmpAmount = 0.0
    var currencyCode  = "608"
    var currency = "PHP"
    var orderId = ""
    var paymentType : PaymentType? = null
    var isOffline  = false
    var isFallback = false
    var customerEmail =  ""
    var deviceModelVersion = ""
    var deviceOsVersion = ""
    var posAppVersion = ""
    var timestamp : Long = 0L
    var cardCaptureMethod = 5
    var device = 0
    var card : CardData? = null
}
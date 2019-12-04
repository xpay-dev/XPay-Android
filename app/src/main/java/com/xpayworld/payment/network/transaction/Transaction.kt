package com.xpayworld.payment.network.transaction

import androidx.room.Embedded
import androidx.room.Entity
import com.xpayworld.payment.data.EMVCardData

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
    //swipe = 0 , emv = 99
    var posEntryMode = 0
    var cardCaptureMethod = 5
    var device = 0
    var emvCard : EMVCardData? = null
}
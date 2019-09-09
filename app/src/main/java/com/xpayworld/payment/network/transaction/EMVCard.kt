package com.xpayworld.payment.network.transaction

import java.util.*

class EMVCard (data : Hashtable<String, String>)  {

    var cardholderName  = ""
    var emvICCData = ""
    var encTrack1 = ""
    var encTrack2 = ""
    var encTrack3 = ""
    var expiryMonth = ""
    var expiryYear = ""
    var expiryDate = "0000"
    var appId = ""

    var appReferredName =  ""
    var posEntryMode = ""
    var ksn = ""
    var epb = ""
    var epbksn = ""
    var maskedPan = ""
    var serviceCode = ""
    var serialNumber = ""
    var trackEncoding = ""
    var cardNumber = 0
    var cardXNumber = "XXX-XXX-XXX-${maskedPan.substring(maskedPan.length-4)}"

    init {
        cardholderName = data["cardholderName"].toString()
        expiryDate =  data["cardholderName"].toString()
        emvICCData = data["C5"].toString()
        ksn = data["C3"].toString()
        epbksn = data["cardholderName"].toString()
        maskedPan = data["cardholderName"].toString()
        appId = data["cardholderName"].toString()
        encTrack1 = data["cardholderName"].toString()
        encTrack2 = data["cardholderName"].toString()
        encTrack3 = data["cardholderName"].toString()
        posEntryMode =data["cardholderName"].toString()
        serviceCode = data["cardholderName"].toString()
    }


}
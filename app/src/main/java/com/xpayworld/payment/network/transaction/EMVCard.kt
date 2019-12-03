package com.xpayworld.payment.network.transaction

import androidx.room.Entity
import java.util.*

@Entity
data class EMVCard (var data: Hashtable<String, String>) {

    var cardholderName = ""
    var emvICCData = ""
    var encTrack1 = ""
    var encTrack2 = ""
    var encTrack3 = ""
    var expiryMonth = ""
    var expiryYear = ""
    var expiryDate = "0000"
    var appId = ""
    var appReferredName = ""
    var ksn = ""
    var epb = ""
    var epbksn = ""
    var maskedPan = ""
    var serviceCode = ""
    var serialNumber = ""
    var trackEncoding = ""
    var cardNumber = ""
    var cardXNumber = ""


    init {
        cardholderName = data["cardholderName"].toString()
        expiryDate = if (data.containsKey("expiryDate")) data["expiryDate"].toString() else data["5F24"].toString()
        emvICCData = data["C2"].toString()
        ksn = if (data.containsKey("C0")) data["C0"].toString() else data["ksn"].toString()
        epb = data["epb"].toString()
        epbksn = data["c1"].toString()
        maskedPan = if (data.containsKey("C4")) data["C4"].toString() else data["maskedPAN"].toString()
        appId = if(data.containsKey("4f")) data["4f"].toString() else  data["9f06"].toString()
        encTrack1 = ""
        encTrack2 =  if (data.containsKey("encTrack2Eq")) data["encTrack2Eq"].toString() else data["encTrack2"].toString()
        encTrack3 = ""

        serviceCode = if(data.containsKey("5F30")) data["5F30"].toString() else  data["serviceCode"].toString()
        cardNumber =if (data.contains("pan")) data["pan"].toString() else  data["5A"].toString()
        cardXNumber = "XXX-XXX-XXX-${maskedPan.substring(maskedPan.length - 4)}"

    }
}
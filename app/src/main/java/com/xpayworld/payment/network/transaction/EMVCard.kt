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
    var posEntryMode = ""
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
        expiryDate = if (data.contains("expiryDate")) data["expiryDate"].toString() else data["5F24"].toString()
        emvICCData = data["C2"].toString()
        ksn = if (data.containsKey("C0")) data["C0"].toString() else data["ksn"].toString()

        epbksn = data["c1"].toString()
        maskedPan = if (data.containsKey("C4")) data["C4"].toString() else data["maskedPAN"].toString()
        appId = data["cardholderName"].toString()
        encTrack1 = data["cardholderName"].toString()
        encTrack2 =  if (data.containsKey("encTrack2Eq")) data["encTrack2Eq"].toString() else data["encTrack2"].toString()
        encTrack3 = data["cardholderName"].toString()
        posEntryMode = data["cardholderName"].toString()
        serviceCode = data["cardholderName"].toString()

        cardXNumber = "XXX-XXX-XXX-${maskedPan.substring(maskedPan.length - 4)}"



//        emvData = EMVCardv2(
//                cardholderName = cardholderName,
//                cardNumber = cardNumber,
//                ksn = ksn,
//                emvICCData = emvICCData ,
//                expiryDate = expiryDate,
//                expiryMonth = expiryMonth,
//                encTrack1 = encTrack1,
//                encTrack2 = encTrack2,
//                posEntryMode = posEntryMode,
//                encTrack3 = encTrack3,
//                appReferredName = appReferredName,
//                epb = epb,
//                epbksn = epbksn,
//                cardXNumber = cardXNumber,
//                expiryYear = expiryYear,
//                trackEncoding = trackEncoding,
//                maskedPan = maskedPan,
//                serialNumber = serialNumber,
//                serviceCode = serviceCode,
//                appId = appId)

//        val formatID = decodeData["formatID"]
//        val maskedPAN = decodeData["maskedPAN"]
//        val PAN = decodeData["pan"]
//        val expiryDate = decodeData["expiryDate"]
//        val cardHolderName = decodeData["cardholderName"]
//        val ksn = decodeData["ksn"]
//        val serviceCode = decodeData["serviceCode"]
//        val encTracks = decodeData["encTracks"]
//        val encTrack1 = decodeData["encTrack1"]
//        val encTrack2 = decodeData["encTrack2"]
//        val encTrack3 = decodeData["encTrack3"]
//        val encWorkingKey = decodeData["encWorkingKey"]
//        val posEntryMode = decodeData["posEntryMode"]
    }
}
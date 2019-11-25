package com.xpayworld.payment.data

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity
data class EMVCardData(

      //  @ColumnInfo(name = "amount")
        var cardholderName: String = "",

      //  @ColumnInfo(name = "trans_number")
        var emvICCData: String = "" ,// how often the plant should be watered, in days
        //@ColumnInfo(name = "trans_date")
        var encTrack1: String = "",

     //   @ColumnInfo(name = "merchant_name")
        var encTrack2: String = "",

     //   @ColumnInfo(name = "merchant_name")
        var encTrack3: String = "",

     //   @ColumnInfo(name = "merchant_name")
        var expiryMonth: String = "",

     //   @ColumnInfo(name = "merchant_name")
        var expiryYear: String = "",

    //    @ColumnInfo(name = "merchant_name")
        var expiryDate: String = "",

     //   @ColumnInfo(name = "merchant_name")
        var appId: String = "",

     //   @ColumnInfo(name = "merchant_name")
        var appReferredName: String = "",

     //   @ColumnInfo(name = "merchant_name")
        var posEntryMode: String = "",

      //  @ColumnInfo(name = "merchant_name")
        var ksn: String = "",

     //   @ColumnInfo(name = "merchant_name")
        var epb: String = "",

      //  @ColumnInfo(name = "merchant_name")
        var epbksn: String = "",

      //  @ColumnInfo(name = "merchant_name")
        var maskedPan: String = "",

     //   @ColumnInfo(name = "merchant_name")
        var serviceCode: String = "",

     //   @ColumnInfo(name = "merchant_name")
        var serialNumber: String = "",

     //   @ColumnInfo(name = "merchant_name")
        var trackEncoding: String = "",

     //   @ColumnInfo(name = "merchant_name")
        var cardNumber: String = "",

    //    @ColumnInfo(name = "merchant_name")
        var cardXNumber: String = ""
        )

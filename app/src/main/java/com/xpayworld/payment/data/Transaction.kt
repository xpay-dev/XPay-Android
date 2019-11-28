package com.xpayworld.payment.data

import androidx.room.*


@Entity
 data class Transaction (

        @ColumnInfo(name = "amount")
        var amount: Double = 0.0,

        @ColumnInfo(name = "trans_date")
        var timestamp: String = "",
        var action: Int = 0,
        var transType: String = "Offline",
        var accounType: Int = 0,
        var posEntry: Int = 0,
        var currencyCode : String = "618",
        var currency: String = "PHP",
        var orderId : String = "",
        var isOffline: Boolean = false,
        var isSync: Boolean = false,
        var customerEmail: String = "",
        var deviceModelVersion: String = "",
        var deviceOsVersion: String = "",
        var posAppVersion: String = "",
        var device: Int = 0,
        @Embedded
        var emvCard : EMVCardData
)
 {
     @PrimaryKey(autoGenerate = true)
     var id: Long = 0
 }


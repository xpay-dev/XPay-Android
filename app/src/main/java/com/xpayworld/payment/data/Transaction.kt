package com.xpayworld.payment.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.xpayworld.payment.network.transaction.EMVCard
import com.xpayworld.payment.network.transaction.PaymentType
import io.reactivex.annotations.NonNull
import java.sql.Timestamp


 @Entity
 data class Transaction (

        val amount: Double = 0.0,

        @ColumnInfo(name = "trans_number")
        val transNumber: String = "" ,// how often the plant should be watered, in days

        @ColumnInfo(name = "trans_date")
        val transDate: String = "",

        @ColumnInfo(name = "merchant_name")
        val merchantName: String = "",

        @ColumnInfo(name = "pos_entry")
        val posEntry: Int= 0,

        val tmpAmount : Double = 0.0,
        val currencyCode : String= "",
        val orderId : String= "",
//        @Embedded
//        val paymentType : PaymentType? = null
        val isOffline: Boolean = false,
        val isFallback: Boolean = false,
        @ColumnInfo(name = "customer_email")
        var customerEmail: String = "",
        var deviceModelVersion: String = "",
        var deviceOsVersion: String = "",
        var posAppVersion: String = "",
        var device: Int = 0
//        @Embedded
//        var emvCard : EMVCard? = null
)
 {
     @PrimaryKey(autoGenerate = true)
     var id: Long = 0
 }
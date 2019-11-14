package com.xpayworld.payment.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.reactivex.annotations.NonNull
import java.sql.Timestamp


@Entity
data class Transaction (
        @ColumnInfo(name = "card_data")
        val cardData: String ,

        val amount: Double,

        val currency: String,

        @ColumnInfo(name = "trans_number")
        val transNumber: String , // how often the plant should be watered, in days

        @ColumnInfo(name = "trans_date")
        val transDate: String,

        @ColumnInfo(name = "merchant_name")
        val merchantName: String,

        @ColumnInfo(name = "pos_entry")
        val posEntry: Int
)
{
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null
}
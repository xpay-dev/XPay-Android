package com.xpayworld.payment.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp


@Entity(tableName = "transactions")
data class Transaction(
        @PrimaryKey @ColumnInfo(name = "id")
        val transId: String,
        val cardData: String,
        val amount: String,
        val currency: Int,
        val transNumber: Int = 7, // how often the plant should be watered, in days
        val timestamp: Timestamp,
        val merchantName :String,
        val posEntry : String
)
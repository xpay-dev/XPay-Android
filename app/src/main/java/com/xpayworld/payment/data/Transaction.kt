package com.xpayworld.payment.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.reactivex.annotations.NonNull
import java.sql.Timestamp


@Entity(tableName = "transactions")
data class Transaction(
        @ColumnInfo(name="card_data")  val cardData: String?,
        @ColumnInfo(name="amount")  val amount: Double?,
        @ColumnInfo(name="currency") val currency: String?,
        @ColumnInfo(name="transaction_number") val transNumber: String?, // how often the plant should be watered, in days
        @ColumnInfo(name="transaction_date") val transDate: String?,
        @ColumnInfo(name="merchant_name") val merchantName :String?,
        @ColumnInfo(name="pos_entry") val posEntry : Int?
){
        @PrimaryKey (autoGenerate = true)
        @ColumnInfo(name = "trans_id")
         var transId: Long = 0
}
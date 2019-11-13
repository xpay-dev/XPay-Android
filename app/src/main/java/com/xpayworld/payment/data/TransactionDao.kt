package com.xpayworld.payment.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface  TransactionDao {

    @Query(value = "SELECT * FROM transactions")
    fun getTransaction(): LiveData<List<Transaction>>

    @Insert
    fun inserTransaction(vararg txn : Transaction)
}
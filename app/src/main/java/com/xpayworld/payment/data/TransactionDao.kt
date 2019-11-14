package com.xpayworld.payment.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface  TransactionDao {

    @Query( "SELECT * FROM `transaction`")
    fun getTransaction():  List<Transaction>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(vararg txn : Transaction)
}
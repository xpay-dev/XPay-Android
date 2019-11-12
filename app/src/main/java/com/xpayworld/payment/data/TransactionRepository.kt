package com.xpayworld.payment.data

class TransactionRepository private  constructor(private val transDao : TransactionDao){

    fun getTransaction() = transDao.getTransaction()

    fun createTransaction(trans : Transaction){
        transDao.inserTransaction(trans)
    }

    companion object{

        @Volatile private  var instance: TransactionRepository? = null

        fun getInstance(transDao: TransactionDao) = instance ?: synchronized(this){
            instance ?: TransactionRepository(transDao).also { instance = it }
        }
    }
}
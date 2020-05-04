package com.xpayworld.payment.util

import android.content.Context


class SharedPrefStorage(context: Context) : SharedPref{
    override fun removeKey(key: String) {
         getEncryptedSharedPreferences(mContext)!!.edit().remove(key).apply()
    }

    var mContext = context
    override fun readMessage(key: String): String {
        return getEncryptedSharedPreferences(mContext)!!.getString(key,"")!!
    }

    override fun isEmpty(key: String): Boolean {
        return getEncryptedSharedPreferences(mContext)!!.getString(key, "") == ""
    }

    override fun writeMessage(key: String, value: String) {
        getEncryptedSharedPreferences(mContext)!!.edit().putString(key,value).apply()
    }
}
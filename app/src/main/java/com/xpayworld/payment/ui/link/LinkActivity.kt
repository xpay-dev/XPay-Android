package com.xpayworld.payment.ui.link

import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.xpayworld.payment.R
import com.xpayworld.payment.databinding.ActivityDashboardBinding
import com.xpayworld.payment.ui.base.kt.BaseActivity
import com.xpayworld.payment.ui.transaction.processTransaction.ARG_AMOUNT
import com.google.gson.GsonBuilder
import com.xpayworld.payment.databinding.ActivityLinkBinding
import com.xpayworld.sdk.EntryPoint
import com.xpayworld.sdk.XPAY_REQUEST
import com.xpayworld.sdk.XpayRequest

class LinkActivity : BaseActivity(){


    override fun initView() {

        val binding: ActivityLinkBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_link)

        val extras = intent.extras
        val request = extras?.getString(XPAY_REQUEST)

        val gson = Gson()

        val data =  gson.fromJson(request,XpayRequest::class.java)

        when (EntryPoint.valueOf(data.entryPoint)) {
            EntryPoint.TRANSACTION -> {

            }
            EntryPoint.ACTIVATION -> {

            }
            EntryPoint.ENTER_PIN -> {

            }
            EntryPoint.PREFERENCE -> {

            }
        }
        Log.e("DATA",data.amountPurchase)

    }
}
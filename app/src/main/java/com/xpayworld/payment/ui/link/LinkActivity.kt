package com.xpayworld.payment.ui.link

import android.content.Intent
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.gson.Gson
import com.xpayworld.payment.R
import com.xpayworld.payment.databinding.ActivityDashboardBinding
import com.xpayworld.payment.ui.base.kt.BaseActivity
import com.xpayworld.payment.ui.transaction.processTransaction.ARG_AMOUNT
import com.google.gson.GsonBuilder
import com.xpayworld.payment.databinding.ActivityLinkBinding
import com.xpayworld.payment.ui.dashboard.DashboardActivity
import com.xpayworld.sdk.EntryPoint
import com.xpayworld.sdk.XPAY_REQUEST
import com.xpayworld.sdk.XpayRequest
import kotlinx.android.synthetic.main.toolbar_main.*

class LinkActivity : BaseActivity(){
    private lateinit var navController: NavController

    override fun initView() {

        val binding: ActivityLinkBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_link)

        val extras = intent.extras
        val request = extras?.getString(XPAY_REQUEST)

        val i = Intent(this,DashboardActivity::class.java)
        i.putExtra(XPAY_REQUEST,request)
        Log.e("ERROR",request)
        startActivity(i)
    }
}
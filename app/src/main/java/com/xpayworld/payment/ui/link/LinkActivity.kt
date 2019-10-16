package com.xpayworld.payment.ui.link

import androidx.databinding.DataBindingUtil
import com.xpayworld.payment.R
import com.xpayworld.payment.databinding.ActivityDashboardBinding
import com.xpayworld.payment.ui.base.kt.BaseActivity

class LinkActivity : BaseActivity(){


    override fun initView() {

        val binding: ActivityDashboardBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_dashboard)
    }
}
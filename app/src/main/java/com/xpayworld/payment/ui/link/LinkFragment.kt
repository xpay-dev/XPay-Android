package com.xpayworld.payment.ui.link

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragment
import com.xpayworld.payment.ui.transaction.processTransaction.ARG_AMOUNT
import com.xpayworld.payment.ui.transaction.processTransaction.BaseDeviceFragment
import com.xpayworld.sdk.XPAY_REQUEST


class LinkFragment : BaseFragment() {

    var request = ""

    override fun getLayout(): Any {
        return R.layout.fragment_link
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
          request = it.getString(XPAY_REQUEST).toString()
        }
    }

    override fun initView(view: View, container: ViewGroup?) {
        Log.e("ERROR",request)

        Log.e("ERROR","asdasdasdasdas")


    }


}

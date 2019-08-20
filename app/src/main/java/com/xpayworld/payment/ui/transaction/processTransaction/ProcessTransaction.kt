package com.xpayworld.payment.ui.transaction.processTransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xpayworld.payment.R

class ProcessTransaction : BaseDeviceFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_process_transaction3, container, false)

        return view
    }
}
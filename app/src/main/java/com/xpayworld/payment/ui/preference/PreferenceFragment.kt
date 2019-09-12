package com.xpayworld.payment.ui.preference


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragment
import com.xpayworld.payment.ui.transaction.processTransaction.BaseDeviceFragment
import kotlinx.android.synthetic.main.fragment_preference.*


class PreferenceFragment : BaseDeviceFragment() {


    override fun getLayout(): Int {
      return R.layout.fragment_preference
    }
    override fun initView(view: View, container: ViewGroup?) {
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = deviceListAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        stopConnection()
    }
}

package com.xpayworld.payment.ui.history

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.xpayworld.payment.ui.base.kt.BaseFragment
import kotlinx.android.synthetic.main.fragment_preference.*

class OfflineTransactionFragment : BaseFragment(){

    private var adapter = TransactionHistoryAdapter()
    override fun initView(view: View, container: ViewGroup?) {

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }
}
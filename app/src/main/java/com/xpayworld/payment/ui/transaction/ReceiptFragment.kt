package com.xpayworld.payment.ui.transaction


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragmentkt
import kotlinx.android.synthetic.main.fragment_receipt.*


class ReceiptFragment : BaseFragmentkt() {
    override fun getLayout(): Int {
        return R.layout.fragment_receipt
    }

    override fun initView(view: View) {

        btnDone.setOnClickListener {
            it.findNavController().navigate(R.id.action_receiptFragment_to_transactionFragment)
            (activity as ToolbarDelegate).showToolbar(true)
        }
    }
}

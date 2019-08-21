package com.xpayworld.payment.ui.transaction.signature

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragment
import com.xpayworld.payment.ui.transaction.processTransaction.ARG_AMOUNT
import com.xpayworld.payment.util.formattedAmount
import kotlinx.android.synthetic.main.fragment_signature.*


class SignatureFragment : BaseFragment() {

    var amountStr = ""

    override fun getLayout(): Int {
        return R.layout.fragment_signature
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            amountStr = it.getString(ARG_AMOUNT).toString()
        }
    }

    private fun bindView() {
        tvAmount.setText(formattedAmount(amountStr))
    }
    override fun initView(view: View , container: ViewGroup?) {
        bindView()

        btnSubmit.setOnClickListener {
            val direction = SignatureFragmentDirections.actionSignatureFragmentToReceiptFragment("")
            it.findNavController().navigate(direction)
        }
    }
}

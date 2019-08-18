package com.xpayworld.payment.ui.transaction.signature

import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragment
import kotlinx.android.synthetic.main.fragment_signature.*


class SignatureFragment : BaseFragment() {
    override fun getLayout(): Int {
        return R.layout.fragment_signature
    }


    override fun initView(view: View , container: ViewGroup?) {

        btnSubmit.setOnClickListener {
            it.findNavController().navigate(R.id.action_signatureFragment_to_receiptFragment)
        }
    }
}

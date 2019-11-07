package com.xpayworld.payment.ui.transaction.signature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.gson.GsonBuilder
import com.xpayworld.payment.databinding.FragmentSignatureBinding
import com.xpayworld.payment.ui.base.kt.BaseFragment
import com.xpayworld.payment.ui.transaction.processTransaction.ARG_AMOUNT
import com.xpayworld.payment.util.formattedAmount
import com.xpayworld.payment.util.transactionResponse
import kotlinx.android.synthetic.main.fragment_signature.*


class SignatureFragment : BaseFragment() {

    var amountStr = ""

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSignatureBinding.inflate(inflater, container, false)
        return binding.root
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
            val gson = GsonBuilder().setPrettyPrinting().create()
            val gsonStr = gson.toJson(transactionResponse)
            val direction = SignatureFragmentDirections.actionSignatureFragmentToReceiptFragment(gsonStr,"")
            it.findNavController().navigate(direction)
        }
    }
}

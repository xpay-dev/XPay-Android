package com.xpayworld.payment.ui.link

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.xpayworld.payment.R
import com.xpayworld.payment.databinding.FragmentEnterAmountBinding
import com.xpayworld.payment.databinding.FragmentLinkBinding
import com.xpayworld.payment.network.transaction.PaymentType
import com.xpayworld.payment.network.transaction.TransactionPurchase
import com.xpayworld.payment.ui.base.kt.BaseFragment
import com.xpayworld.payment.util.InjectorUtil
import com.xpayworld.payment.util.paymentType
import com.xpayworld.payment.util.transaction
import com.xpayworld.sdk.EntryPoint
import com.xpayworld.sdk.EntryPoint.*
import com.xpayworld.sdk.XPAY_REQUEST
import com.xpayworld.sdk.XpayRequest



class LinkFragment : BaseFragment() {

    var request = ""

    private val viewModel: LinkViewModel by viewModels {
        InjectorUtil.provideLinkViewModel(requireContext())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
          request = it.getString(XPAY_REQUEST).toString()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLinkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView(view: View, container: ViewGroup?) {

        val gson = Gson()

        val data =  gson.fromJson(request, XpayRequest::class.java)

        viewModel.loadingVisibility.observe(this, Observer {
            if (it) showProgress() else hideProgress()
        })

        // Network Error
        viewModel.networkError.observe(this, Observer {
            showNetworkError(title = it)
        })

        viewModel.requestError.observe(this, Observer {


        })

        viewModel.navigateToNextEntry.observe( this , Observer {

            when (valueOf(data.entryPoint)) {
                TRANSACTION -> {
                    paymentType  = PaymentType.CREDIT(TransactionPurchase.Action.EMV)
                    transaction.amount = data.amountPurchase
                    val strAmount = "${data.amountPurchase}".removePrefix(".")
                    val direction = LinkFragmentDirections.actionLinkFragmentToProcessTranactionFragment(strAmount)
                    findNavController().navigate(direction)
                }

                ACTIVATION -> {

                }

                ENTER_PIN -> {

                }

                PREFERENCE -> {

                }
            }
        })
    }


}

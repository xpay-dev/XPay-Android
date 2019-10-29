package com.xpayworld.payment.ui.link

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson

import com.xpayworld.payment.R
import com.xpayworld.payment.network.transaction.PaymentType
import com.xpayworld.payment.network.transaction.TransactionPurchase
import com.xpayworld.payment.ui.base.kt.BaseFragment
import com.xpayworld.payment.ui.enterPin.EnterPinViewModel
import com.xpayworld.payment.ui.transaction.enterAmount.EnterAmountFragmentDirections
import com.xpayworld.payment.ui.transaction.processTransaction.ARG_AMOUNT
import com.xpayworld.payment.ui.transaction.processTransaction.BaseDeviceFragment
import com.xpayworld.payment.util.InjectorUtil
import com.xpayworld.payment.util.paymentType
import com.xpayworld.payment.util.transaction
import com.xpayworld.sdk.EntryPoint
import com.xpayworld.sdk.XPAY_REQUEST
import com.xpayworld.sdk.XpayRequest
import kotlinx.android.synthetic.main.fragment_process_transaction.*


class LinkFragment : BaseFragment() {

    var request = ""

    private val viewModel: LinkViewModel by viewModels {
        InjectorUtil.provideLinkViewModel(requireContext())
    }

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

        val gson = Gson()

        val data =  gson.fromJson(request, XpayRequest::class.java)

        viewModel.loadingVisibility.observe(this, Observer {
            if (it) showProgress() else hideProgress()
        })

        // Network Error
        viewModel.networkError.observe(this, Observer {
            btnCancel.visibility = View.INVISIBLE
            showNetworkError(title = it)
        })

        viewModel.requestError.observe(this, Observer {


        })

//        when (EntryPoint.valueOf(data.entryPoint)) {
//
//            EntryPoint.TRANSACTION -> {
//                paymentType  = PaymentType.CREDIT(TransactionPurchase.Action.EMV)
//                transaction.amount = ( data.amountPurchase.toInt()/100.0)
//                val direction = LinkFragmentDirections.actionLinkFragmentToTransactionFragment(data.amountPurchase)
//                findNavController().navigate(direction)
//            }
//
//            EntryPoint.ACTIVATION -> {
//
//            }
//
//            EntryPoint.ENTER_PIN -> {
//
//            }
//
//            EntryPoint.PREFERENCE -> {
//
//            }
//        }

    }


}

package com.xpayworld.payment.ui.link

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.xpayworld.payment.R
import com.xpayworld.payment.databinding.FragmentEnterAmountBinding
import com.xpayworld.payment.databinding.FragmentLinkBinding
import com.xpayworld.payment.ui.base.kt.BaseFragment
import com.xpayworld.payment.util.InjectorUtil
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

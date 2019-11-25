package com.xpayworld.payment.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xpayworld.payment.databinding.FragmentTransactionHistoryBinding
import com.xpayworld.payment.network.TransactionResponse
import com.xpayworld.payment.ui.base.kt.BaseFragment
import com.xpayworld.payment.util.InjectorUtil
import kotlinx.android.synthetic.main.fragment_preference.*
import kotlinx.android.synthetic.main.fragment_preference.recyclerView
import kotlinx.android.synthetic.main.fragment_transaction_history.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OfflineTransactionFragment : BaseFragment(){

    private val viewModel: TransactionHistoryViewModel by viewModels {
        InjectorUtil.provideTransactionHistoryViewModel(requireContext())
    }

    private var adapter = TransactionHistoryAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTransactionHistoryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this@OfflineTransactionFragment
        return binding.root
    }


    override fun initView(view: View, container: ViewGroup?) {

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter


        GlobalScope.launch {
            val txn =   InjectorUtil.getTransactionRepository(requireContext()).getTransaction()
            txn.forEach {
                val data = TransactionResponse()
                data.transType = "Offline"
                data.timestamp = it.timestamp
                data.total = "${it.amount}"
                data.currency = it.currency
                data.transNumber = it.transNumber

                println("EMV >>> ${it.emvCard.emvICCData}")
                println("AMOUNT >>> ${it.amount}")
                println("CURRENCY >>> ${it.currency}")
                println("TIMESTAMP >>> ${it.timestamp}")

                println("TRANS NUMBER >>> ${it.transNumber}")
//                trans.add(data)
            }
//            transResponse.value = trans
        }

//        var trans : arra<TransactionResponse> = ()

        var trans = arrayListOf<TransactionResponse>()
        GlobalScope.launch {
            val txn =   InjectorUtil.getTransactionRepository(requireContext()).getTransaction()
            txn.forEach {
                val data = TransactionResponse()
                data.transType = "Offline"
                data.timestamp = it.timestamp
                data.total = "${it.amount}"
                data.currency = it.currency
                data.transNumber = it.transNumber
                trans.add(data)
            }
            adapter.updatePostList(trans)
        }
//       viewModel.callOfflineTransaction()
//
//        viewModel.transResponse.observe(this , Observer {
//            recyclerView.visibility = View.VISIBLE
//
//            tvStatus.visibility = View.GONE
//        })
    }





}
package com.xpayworld.payment.ui.history


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragment
import com.xpayworld.payment.ui.enterPin.EnterPinViewModel
import com.xpayworld.payment.util.InjectorUtil

class TransactionHistoryFragment : BaseFragment() {

    private val viewModel: TransactionHistoryViewModel by viewModels {
        InjectorUtil.provideTransactionHistoryViewModel(requireContext())
    }
    override fun getLayout(): Int {
        return R.layout.fragment_transaction_history
    }

    override fun initView(view: View, container: ViewGroup?) {



        viewModel.loadingVisibility.observe(this, Observer {
            if (it) showProgress() else hideProgress()
        })

        viewModel.callTransLookUp()

    }

}

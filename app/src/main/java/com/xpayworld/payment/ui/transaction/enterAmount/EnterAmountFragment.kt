package com.xpayworld.payment.ui.transaction.enterAmount

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.xpayworld.payment.R
import com.xpayworld.payment.network.transaction.PaymentType
import com.xpayworld.payment.network.transaction.TransactionPurchase
import com.xpayworld.payment.ui.base.kt.BaseFragment
import com.xpayworld.payment.ui.transaction.DrawerLocker
import com.xpayworld.payment.ui.transaction.processTransaction.ARG_AMOUNT
import com.xpayworld.payment.util.InjectorUtil
import com.xpayworld.payment.util.formattedAmount
import kotlinx.android.synthetic.main.fragment_enter_amount.*
import kotlinx.android.synthetic.main.view_enter_amount.*





class EnterAmountFragment : BaseFragment() {


    var numpad = listOf<Button>()
    var amountStr = ""

    private  val viewModel : EnterAmountViewModel by viewModels {
        InjectorUtil.provideEnterAmountViewModelFactory(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            amountStr = it.getString(ARG_AMOUNT).toString()
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_enter_amount
    }
    override fun initView(view: View,container: ViewGroup?) {
        // Numpad Button
        // Numpad Button Image

        numpad = listOf(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0)

        // Input
        numpad.forEach { it.setOnClickListener(viewModel.numpadClickListener) }

        btnCredit.setOnClickListener  (viewModel.transTypeClickListener)
        btnDebit.setOnClickListener  (viewModel.transTypeClickListener)
        btnClear.setOnClickListener(viewModel.clearClickListener)
        btnOk.setOnClickListener(viewModel.okClickListener)

        var paymentType: PaymentType = PaymentType.DEBIT(PaymentType.DebitTransaction.SALE, TransactionPurchase.AccountType.SAVINGS)
        when (paymentType) {
            is PaymentType.DEBIT -> {
                paymentType.debit.stringValue
                paymentType.accountType
            }

        }

        // output
        viewModel.transTypeSetResource.observe(this , Observer {
            btnCredit.setBackgroundResource(it[0])
            btnDebit.setBackgroundResource(it[1])
        })

        viewModel.displayAmount.observe(this , Observer { tvAmount.text = it })

        viewModel.deviceError.observe(this , Observer { msg ->
            showNetworkError(msg.first,msg.second)
        })
    }



    override fun onResume() {
        super.onResume()

        (activity as DrawerLocker).drawerEnabled(true)
    }

}


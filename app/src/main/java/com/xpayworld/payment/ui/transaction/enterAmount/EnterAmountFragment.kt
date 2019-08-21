package com.xpayworld.payment.ui.transaction.enterAmount

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragment
import com.xpayworld.payment.ui.transaction.DrawerLocker
import com.xpayworld.payment.ui.transaction.processTransaction.ARG_AMOUNT
import com.xpayworld.payment.util.formattedAmount
import kotlinx.android.synthetic.main.fragment_enter_amount.*
import kotlinx.android.synthetic.main.view_enter_amount.*





class EnterAmountFragment : BaseFragment() {

    var numpad = listOf<Button>()

    private lateinit var viewModel: EnterAmountViewModel
    var amountStr = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            amountStr = it.getString(ARG_AMOUNT).toString()
        }
    }


    override fun initView(view: View,container: ViewGroup?) {
        // Initialized view


        // Numpad Button
        // Numpad Button Image

        viewModel= ViewModelProviders.of(this , EnterAmountViewModelFactory(amountStr)).get(EnterAmountViewModel::class.java)
        numpad = listOf(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0)

        // Input
        numpad.forEach { it.setOnClickListener(viewModel.numpadClickListener) }

        btnCredit.setOnClickListener  (viewModel.transTypeClickListener)
        btnDebit.setOnClickListener  (viewModel.transTypeClickListener)
        btnClear.setOnClickListener(viewModel.clearClickListener)
        btnOk.setOnClickListener(viewModel.okClickListener)


        // output
        viewModel.transTypeSetResource.observe(this , Observer {
            btnCredit.setBackgroundResource(it[0])
            btnDebit.setBackgroundResource(it[1])
        })

        viewModel.displayAmount.observe(this , Observer { tvAmount.text = it })


    }

    override fun getLayout(): Int {
        return R.layout.fragment_enter_amount
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as DrawerLocker).drawerEnabled(true)
    }
}


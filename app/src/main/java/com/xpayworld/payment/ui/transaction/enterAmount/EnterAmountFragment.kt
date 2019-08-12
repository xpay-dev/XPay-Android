package com.xpayworld.payment.ui.transaction.enterAmount

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragment
import com.xpayworld.payment.ui.transaction.DrawerLocker
import com.xpayworld.payment.util.CustomDialog
import com.xpayworld.payment.util.InjectorUtil
import kotlinx.android.synthetic.main.fragment_enter_amount.*
import kotlinx.android.synthetic.main.view_enter_amount.*



class EnterAmountFragment : BaseFragment() {

    var numpad = listOf<Button>()

    private lateinit var viewModel: EnterAmountViewModel


    override fun initView(view: View) {
        // Numpad Button
        // Numpad Button Image

        viewModel= ViewModelProviders.of(activity!!).get(EnterAmountViewModel::class.java)
        numpad = listOf(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0)

        // Input
        numpad.forEach { it.setOnClickListener(viewModel.numpadClickListener) }

        btnCredit.setOnClickListener  (viewModel.transTypeClickListener)
        btnDebit.setOnClickListener  (viewModel.transTypeClickListener)
        btnClear.setOnClickListener(viewModel.clearClickListener)
        btnOk.setOnClickListener(viewModel.okClickListener)

        // output
        viewModel.transTypeSetPadding.observe(this , Observer{
            btnCredit.setPadding(0, it, 0, 0)
            btnDebit.setPadding(0, it, 0, 0)
        })

        viewModel.transTypeSetResource.observe(this , Observer {
            btnCredit.setBackgroundResource(it[0])
            btnDebit.setBackgroundResource(it[1])
        })

        viewModel.displayAmount.observe(this , Observer { tvAmount.text = it })

    }

    override fun getLayout(): Int {
        return R.layout.fragment_enter_amount
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as DrawerLocker).drawerEnabled(true)
    }
}


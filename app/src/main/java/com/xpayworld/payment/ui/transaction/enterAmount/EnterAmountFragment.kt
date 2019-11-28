package com.xpayworld.payment.ui.transaction.enterAmount

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.xpayworld.payment.R
import com.xpayworld.payment.databinding.FragmentEnterAmountBinding
import com.xpayworld.payment.databinding.FragmentEnterPinBinding
import com.xpayworld.payment.databinding.ViewEnterAmountBinding
import com.xpayworld.payment.network.transaction.PaymentType
import com.xpayworld.payment.network.transaction.TransactionPurchase
import com.xpayworld.payment.ui.base.kt.BaseFragment
import com.xpayworld.payment.ui.dashboard.DrawerLocker
import com.xpayworld.payment.ui.history.DispatchGroup
import com.xpayworld.payment.ui.transaction.processTransaction.ARG_AMOUNT
import com.xpayworld.payment.util.InjectorUtil
import kotlinx.android.synthetic.main.fragment_enter_amount.*
import kotlinx.android.synthetic.main.view_enter_amount.*
import kotlinx.android.synthetic.main.view_number_pad.*
import java.util.*
import kotlin.concurrent.schedule


class EnterAmountFragment : BaseFragment() {


    var numpad = listOf<Button>()
    var amountStr = ""

    private  val viewModel : EnterAmountViewModel by viewModels {
        InjectorUtil.provideEnterAmountViewModelFactory(requireContext())
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEnterAmountBinding.inflate(inflater, container, false)
        binding.vwNumpad.viewModel = viewModel
        binding.lifecycleOwner = this@EnterAmountFragment
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            amountStr = it.getString(ARG_AMOUNT).toString()
        }
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

        callforBatchUpload()


        // output
        viewModel.transTypeSetResource.observe(this , Observer {
            btnCredit.setBackgroundResource(it[0])
            btnDebit.setBackgroundResource(it[1])
        })

        viewModel.deviceError.observe(this , Observer { msg ->
            showError(msg.first,msg.second)
        })
    }



    fun callforBatchUpload(){
        val txn =   InjectorUtil.getTransactionRepository(requireContext()).getTransaction()


            val dispatch = DispatchGroup()

            for (transaction in txn) {

                dispatch.enter()

                Timer().schedule(10){
                    println("finish")
                    dispatch.leave()
                }
            }
            dispatch.notify {
                println("finish")
            }

    }

    override fun onResume() {
        super.onResume()

        (activity as DrawerLocker).drawerEnabled(true)
    }

}


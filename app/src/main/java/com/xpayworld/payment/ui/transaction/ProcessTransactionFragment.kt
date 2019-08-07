package com.xpayworld.payment.ui.transaction


import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.navigation.findNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragmentkt
import com.xpayworld.payment.util.formattedAmount
import kotlinx.android.synthetic.main.fragment_process_transaction.*


private const val ARG_AMOUNT = "amount"
class ProcessTransactionFragment : BaseFragmentkt() {

    private var strAmount : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        strAmount = it.getString(ARG_AMOUNT)
        }
    }
    override fun getLayout(): Int {
        return R.layout.fragment_process_transaction
    }

    override fun initView(view: View) {
        var isCanceled =  false
        (activity as DrawerLocker).drawerEnabled(false)
         tvAmount.text = strAmount?.let {formattedAmount(it)}
        btnCancel.setOnClickListener {
            isCanceled = true
            view.findNavController().popBackStack()
            (activity as DrawerLocker).drawerEnabled(true)

        }


        Handler().postDelayed({
            if (!isCanceled) {
                view.findNavController().navigate(R.id.action_process_transaction_to_signatureFragment)
            }
        },3000)
    }
}

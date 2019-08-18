package com.xpayworld.payment.ui.transaction.processTransaction


import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.util.formattedAmount
import kotlinx.android.synthetic.main.fragment_process_transaction.*

private const val ARG_AMOUNT = "amount"

class ProcessTransactionFragment : Fragment() {

    var strAmount = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            strAmount = it.getString(ARG_AMOUNT).toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_process_transaction, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val anim = imgProcess?.drawable as AnimationDrawable

        if (activity is ProcessTransactionActivity){
           val activity = (activity as ProcessTransactionActivity)

            activity.startAnimation.observe(this , Observer {
                if (it) anim.start() else anim.stop()
            })

            activity.onResult.observe(this, Observer {
                val direction = ProcessTransactionFragmentDirections.actionProcessTransactionFragment2ToSignatureFragment()
                if (it) {
                    view.findNavController().navigate(direction)
                }
            })
        }


        tvAmount.text = strAmount?.let { formattedAmount(it) }
        btnCancel.setOnClickListener {
            if (activity is ProcessTransactionActivity) {
                (activity as ProcessTransactionActivity).stopConnection()
                activity!!.finish()
            }

        }

    }
}

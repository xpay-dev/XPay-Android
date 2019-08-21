package com.xpayworld.payment.ui.transaction.processTransaction

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.transaction.DrawerLocker
import com.xpayworld.payment.ui.transaction.ToolbarDelegate
import com.xpayworld.payment.util.formattedAmount
import kotlinx.android.synthetic.main.fragment_process_transaction.*


class ProcessTransactionFragment : BaseDeviceFragment()  {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_process_transaction, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvAmount.text = amountStr?.let { formattedAmount(it) }
        (activity as DrawerLocker).drawerEnabled(false)

        startAnimation.observe(this , Observer {
            if (it) imageProcess().start() else imageProcess().stop()
        })

        toolbarTitle.observe(this , Observer {
            (activity as ToolbarDelegate).setTitle(it)
        })

        onResult.observe(this , Observer {
           val direction  = ProcessTransactionFragmentDirections.actionProcessTransactionToSignatureFragment(amountStr)
            if (it) view.findNavController().navigate(direction)
        })
    }

    private fun imageProcess(): AnimationDrawable{
        val img = view!!.findViewById<ImageView>(R.id.imgProcess)
      return  img?.drawable as AnimationDrawable

    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}
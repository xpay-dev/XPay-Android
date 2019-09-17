package com.xpayworld.payment.ui.transaction.processTransaction

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.transaction.DrawerLocker
import com.xpayworld.payment.ui.transaction.ToolbarDelegate
import com.xpayworld.payment.util.formattedAmount
import kotlinx.android.synthetic.main.fragment_process_transaction.*


class ProcessTransactionFragment : BaseDeviceFragment()  {


    var viewModel : ProcessTransactionViewModel? = null
    override fun getLayout(): Any {
         return R.layout.fragment_process_transaction
    }

    override fun initView(view: View, container: ViewGroup?) {
        tvAmount.text = formattedAmount(amountStr)

        viewModel = ViewModelProviders.of(this).get(ProcessTransactionViewModel::class.java)

        startAnimation.observe(this , Observer {
            if (it) imageProcess().start() else imageProcess().stop()
        })

        toolbarTitle.observe(this , Observer((activity as ToolbarDelegate)::setTitle))

        cancelVisibility.observe(this , Observer { btnCancel.visibility })

        viewModel?.loadingVisibility?.observe(this, Observer{
            isShow -> if (isShow == true) showProgress() else hideProgress()
        })

        // Calling Transaction API
        onProcessTransaction.observe(this, Observer {
            viewModel?.callTransactionAPI()
        })

        // Transaction API Result
        viewModel?.transactionApiResponse?.observe(this , Observer {
            onlineProcessResult.value = it
        })

        // Device Transaction Result
        onTransactionResult.observe(this , Observer {
            val direction = ProcessTransactionFragmentDirections.actionProcessTransactionToSignatureFragment(amountStr)
            if (it) view.findNavController().navigate(direction)
        })

        cancelTitle.observe(this  , Observer {
            btnCancel.text = it })

        btnCancel.setOnClickListener {
            stopConnection()
            view.findNavController().popBackStack(R.id.transactionFragment,true)
        }
    }




    private fun imageProcess(): AnimationDrawable{
        val img = view!!.findViewById<ImageView>(R.id.imgProcess)
       return  img?.drawable as AnimationDrawable
    }


    override fun onResume() {
        super.onResume()

        (activity as DrawerLocker).drawerEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}
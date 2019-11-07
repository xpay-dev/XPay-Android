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
import com.xpayworld.payment.databinding.FragmentProcessTransactionBinding
import com.xpayworld.payment.databinding.FragmentReceiptBinding
import com.xpayworld.payment.ui.dashboard.DrawerLocker
import com.xpayworld.payment.ui.dashboard.ToolbarDelegate
import com.xpayworld.payment.util.formattedAmount
import com.xpayworld.payment.util.isSDK
import kotlinx.android.synthetic.main.fragment_process_transaction.*


@Suppress("UNCHECKED_CAST")
class ProcessTransactionFragment : BaseDeviceFragment() {

    var viewModel: ProcessTransactionViewModel? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProcessTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView(view: View, container: ViewGroup?) {
        if (amountStr.contains("."))
            amountStr = amountStr.replace(".","")

        tvAmount.text = formattedAmount(amountStr)

        viewModel = ViewModelProviders.of(this).get(ProcessTransactionViewModel::class.java)

        startAnimation.observe(this, Observer {
            if (it) imageProcess().start() else imageProcess().stop()
        })

        toolbarTitle.observe(this, Observer((activity as ToolbarDelegate)::setTitle))

        cancelVisibility.observe(this, Observer { btnCancel.visibility })

        checkBluetoothPermission.observe(this , Observer {
            if (it) {
                showError("Bluetooth is denied", "This application requires bluetooth connection to process transaction, Please go to settings to enable bluetooth permission")
            }
        })

        //Progress dialog
        viewModel?.loadingVisibility?.observe(this, Observer { isShow ->
            if (isShow == true) showProgress() else hideProgress()
        })

        // Network Error
        viewModel?.networkError?.observe(this, Observer {
            btnCancel.visibility = View.INVISIBLE
            showNetworkError(title = it)
        })

        viewModel?.requestError?.observe(this, Observer {
            if (it is Pair<*, *>){
                val message = it as Pair<Double,String>
                btnCancel.visibility = View.INVISIBLE
                showError(title = "REQUEST ERROR ${message.first}", message = message.second, callBack = {
                    if (isSDK){
                        activity?.finish()
                    } else view.findNavController().popBackStack(R.id.transactionFragment, true)
              })
            }
        })



        // Calling Transaction API
        onProcessTransaction.observe(this, Observer {
            viewModel?.callTransactionAPI()
        })

        // Transaction API Result
        viewModel?.onlineAuthResult?.observe(this, Observer {
            onlineProcessResult.value = it
        })

        // Device Transaction Result
        onTransactionResult.observe(this, Observer {
            val direction = ProcessTransactionFragmentDirections.actionProcessTransactionToSignatureFragment(amountStr)
            if (it) view.findNavController().navigate(direction)
        })

        cancelTitle.observe(this, Observer {
            btnCancel.text = it
        })

        btnCancel.setOnClickListener {
            stopConnection()
            view.findNavController().popBackStack(R.id.transactionFragment, true)
        }
    }

    private fun imageProcess(): AnimationDrawable {
        val img = view!!.findViewById<ImageView>(R.id.imgProcess)
        return img?.drawable as AnimationDrawable
    }


    override fun onResume() {
        super.onResume()

        (activity as DrawerLocker).drawerEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}
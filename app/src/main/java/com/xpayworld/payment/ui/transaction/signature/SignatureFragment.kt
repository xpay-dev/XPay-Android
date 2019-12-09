package com.xpayworld.payment.ui.transaction.signature

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.gson.GsonBuilder
import com.xpayworld.payment.databinding.FragmentSignatureBinding
import com.xpayworld.payment.ui.base.kt.BaseFragment
import com.xpayworld.payment.ui.transaction.processTransaction.ARG_AMOUNT
import com.xpayworld.payment.util.InjectorUtil
import com.xpayworld.payment.util.formattedAmount
import com.xpayworld.payment.util.transactionResponse
import kotlinx.android.synthetic.main.fragment_signature.*
import java.io.ByteArrayOutputStream


class SignatureFragment : BaseFragment() {

    var amountStr = ""
    private val viewModel: SignatureViewModel by viewModels {
        InjectorUtil.provideSignatureViewModel(requireContext())
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSignatureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            amountStr = it.getString(ARG_AMOUNT).toString()
        }
    }


    override fun initView(view: View , container: ViewGroup?) {

        tvAmount.text = formattedAmount(amountStr)

        btnSubmit.setOnClickListener {

            if (!vwSignature.isBitmapEmpty) {
            val sign = vwSignature.signatureBitmap
            val baos = ByteArrayOutputStream()
            sign.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val b = baos.toByteArray()
            val imageStr = b.toHexString()

            viewModel.callSignatureAPI(imgStr = imageStr , imageLen = "${imageStr.length}",transNumber = transactionResponse!!.transNumber!!)
            val gson = GsonBuilder().setPrettyPrinting().create()
            val gsonStr = gson.toJson(transactionResponse)
            val respStr = gson.toJson(transactionResponse?.result)
            val direction = SignatureFragmentDirections.actionSignatureFragmentToReceiptFragment(gsonStr,respStr)
            it.findNavController().navigate(direction)
            }
        }

        btnClear.setOnClickListener {
            vwSignature.clearCanvas()
            lblSignature.visibility = View.VISIBLE
        }

        vwSignature.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_MOVE) { //do something
                lblSignature.visibility = View.INVISIBLE
            }
            true
        }
    }



    fun ByteArray.toHexString() : String {
        return this.joinToString("") {
            java.lang.String.format("%02x", it)
        }
    }
}

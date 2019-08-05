package com.xpayworld.payment.ui.transaction

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragmentkt
import com.xpayworld.payment.util.SharedPrefStorage
import com.xpayworld.payment.util.formattedAmount
import kotlinx.android.synthetic.main.fragment_enter_amount.*
import kotlinx.android.synthetic.main.view_enter_amount.*
import java.text.DecimalFormat


class EnterAmountFragment : BaseFragmentkt() {

    var numpad = listOf<Button>()
    var numpadImg = listOf<ImageButton>()
    var strAmount = ""
    var formatedAmount = ""


    override fun getLayout(): Int {
        return R.layout.fragment_enter_amount
    }

    override fun initView(view: View) {
        shouldCheckActivationKey()

        shouldAdjustPaddigTop()
        // Initial Display
        tvAmount.text = formattedAmount(strAmount)

        // Numpad Button
        numpad = listOf(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0)
        numpad.forEach { btn: Button -> btn.setOnClickListener(OnClickButton()) }

        // Numpad Button Image
        numpadImg = listOf(btnClear, btnOk)
        numpadImg.forEach { btn: ImageButton -> btn.setOnClickListener(OnClickImgButton()) }

        btnCredit.setOnClickListener {

            btnCredit.setBackgroundResource(R.drawable.tab_indenticator)
            btnDebit.setBackgroundResource(R.drawable.tab_indenticator_clear)
            //Note: should execute the setBackgroundResource first before adjust the padding
            shouldAdjustPaddigTop()
        }

        btnDebit.setOnClickListener {
            btnDebit.setBackgroundResource(R.drawable.tab_indenticator)
            btnCredit.setBackgroundResource(R.drawable.tab_indenticator_clear)
            //Note: should execute the setBackgroundResource first before adjust the padding
            shouldAdjustPaddigTop()
        }
    }

    private fun shouldCheckActivationKey(){
        val sharedPref = context?.let { it -> SharedPrefStorage(it) }
        if (sharedPref!!.isEmpty("activationKey")){
            findNavController().navigate(R.id.activationFragment)
        }
        else{

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as DrawerLocker).drawerEnabled(true)
    }


    inner class OnClickImgButton : View.OnClickListener {
        override fun onClick(v: View?) {
            when (v as ImageButton) {
                btnClear -> {
                    strAmount = strAmount.dropLast(1)
                    tvAmount.text = formattedAmount(strAmount)
                }
                btnOk -> {
                    if (strAmount.isEmpty()) return

                    val direction = EnterAmountFragmentDirections.actionEnterAmountFragmentToProcessTranactionFragment(strAmount)
                    v.findNavController().navigate(direction)
                }
            }
        }
    }

    inner class OnClickButton : View.OnClickListener {
        override fun onClick(v: View?) {
            val len = strAmount.length
            if (len == 8) return
            if (len == 0 && (v as Button).text == "0") return

            strAmount += (v as Button).text
           tvAmount.text = formattedAmount(strAmount)
        }
    }
    private fun shouldAdjustPaddigTop() {
        btnCredit.setPadding(0, 10, 0, 0)
        btnDebit.setPadding(0, 10, 0, 0)
    }
}


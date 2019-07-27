@file:JvmName("EnterAmountKt")

package com.xpayworld.payment.ui.transaction

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragmentkt
import kotlinx.android.synthetic.main.fragment_enter_amount.*
import kotlinx.android.synthetic.main.view_enter_amount.*
import java.text.DecimalFormat


class EnterAmount : BaseFragmentkt() {

    var numpad = listOf<Button>()
    var numpadImg = listOf<ImageButton>()
    var strAmount = ""
    var formatedAmount = ""



    override fun initView() {
        numpad = listOf(btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9)
        numpad.forEach { btn: Button -> btn.setOnClickListener(OnClickButton())}
        numpadImg = listOf(btnClear,btnOk)
        numpadImg.forEach { btn : ImageButton -> btn.setOnClickListener(OnClickImgButton())}

        shouldAdjustPaddigTop()
        btnCredit.setOnClickListener {
            shouldAdjustPaddigTop()
            btnCredit.setBackgroundResource(R.drawable.tab_indenticator)
            btnDebit.setBackgroundResource(R.drawable.tab_indenticator_clear)
        }

        btnDebit.setOnClickListener {
            shouldAdjustPaddigTop()
            btnDebit.setBackgroundResource(R.drawable.tab_indenticator)
            btnCredit.setBackgroundResource(R.drawable.tab_indenticator_clear)
        }

    }

    override fun getLayout(): Int {
        return R.layout.fragment_enter_amount
    }

    inner  class OnClickImgButton : View.OnClickListener{
        override fun onClick(v: View?) {
            when(v as ImageButton){
                btnClear ->{
                    strAmount = strAmount.dropLast(1)
                    showAmount()
                }
                btnOk ->{
                    //navigate to process transaction

                }
            }
        }
    }

    inner class OnClickButton : View.OnClickListener {
        override fun onClick(v: View?) {
            if (strAmount.length == 8) return

            strAmount += (v as Button).text
            showAmount()
        }
    }

   private fun showAmount(){
        val len = strAmount.length
        val df = DecimalFormat("###,###,###.##")

        if (len in 1..8){
            this.formatedAmount =  df.format((strAmount.toDouble()/100))
        }
        else if (len == 0){
            this.formatedAmount = "0.00"
        }
       tvAmount.text = formatedAmount
    }

    private fun shouldAdjustPaddigTop(){
        btnCredit.setPadding(0,10,0,0)
        btnDebit.setPadding(0,10,0,0)
    }
}
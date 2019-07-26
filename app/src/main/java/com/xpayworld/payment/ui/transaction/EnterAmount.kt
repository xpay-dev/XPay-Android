@file:JvmName("EnterAmountKt")

package com.xpayworld.payment.ui.transaction

import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragmentkt
import kotlinx.android.synthetic.main.fragment_enter_amount.*

class EnterAmount : BaseFragmentkt() {



    override fun initView() {

        shouldAdjustPaddigTop()
        btnCredit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                btnCredit.setBackgroundResource(R.drawable.tab_indenticator)
                btnDebit.setBackgroundResource(R.drawable.tab_indenticator_clear)
                shouldAdjustPaddigTop()
            }
        })
        btnDebit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                btnDebit.setBackgroundResource(R.drawable.tab_indenticator)
                btnCredit.setBackgroundResource(R.drawable.tab_indenticator_clear)
                shouldAdjustPaddigTop()
            }
        })
    }

    override fun getLayout(): Int {
        return R.layout.fragment_enter_amount
    }

    fun shouldAdjustPaddigTop(){
        btnCredit.setPadding(0,10,0,0)
        btnDebit.setPadding(0,10,0,0)
    }
}
package com.xpayworld.payment.ui.enterPin

import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragmentkt
import com.xpayworld.payment.ui.transaction.DrawerLocker
import com.xpayworld.payment.ui.transaction.ToolbarDelegate
import com.xpayworld.payment.util.SharedPrefStorage
import kotlinx.android.synthetic.main.fragment_enter_pin.*

class EnterPinFragment : BaseFragmentkt() {

    var numpad = listOf<Button>()
    var strCode = ""
    var codeImg = listOf<ImageView>()

    override fun getLayout(): Int {
        return R.layout.fragment_enter_pin
    }

    override fun initView(v: View) {

        val sharedPref = context?.let { it -> SharedPrefStorage(it) }

        if (sharedPref!!.isEmpty("activationKey")){
                findNavController().navigate(R.id.activationFragment)
            return
        }

        (activity as ToolbarDelegate).showToolbar(false)
        codeImg = listOf(img1, img2, img3, img4)
        numpad = listOf(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0)
        numpad.forEach { it.setOnClickListener(OnClickNumpad()) }

        btnSubmit.setOnClickListener(OnClickSubmit())
        btnClear.setOnClickListener(OnClickClear())
    }

    inner class OnClickSubmit : View.OnClickListener {
        override fun onClick(v: View?) {
            val direction = EnterPinFragmentDirections.actionEnterPinFragmentToTransactionFragment()
            v?.findNavController()?.navigate(direction)
            (activity as ToolbarDelegate).showToolbar(true)
        }
    }

    internal inner class OnClickClear : View.OnClickListener {
        override fun onClick(p0: View?) {
            print(strCode.length)
            strCode = strCode.dropLast(1)
            shouldUpdateCodeImage()
        }
    }

    inner class OnClickNumpad : View.OnClickListener {
        override fun onClick(v: View?) {
            if (strCode.length <= 3) {
                strCode += (v as Button).text
            }
            shouldUpdateCodeImage()
        }
    }

    fun shouldUpdateCodeImage() {
        for (x in 0 until codeImg.size) {
            val img = if (strCode.length >= x + 1) R.drawable.ic_pin_cirlce_dot else R.drawable.ic_pin_circle
            codeImg[x].setBackgroundResource(img)

        }
    }
}
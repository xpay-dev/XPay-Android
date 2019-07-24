package com.xpayworld.payment.ui.enterPin

import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseActivitykt
import kotlinx.android.synthetic.main.activity_enter_pin.*

class EnterPinActivity: BaseActivitykt(){

    var numpad = listOf<Button>()

    var strCode = ""
    var codeImg = listOf<ImageView>()
    override fun getLayout(): Int {
        return R.layout.activity_enter_pin
    }

    override fun initView() {
        codeImg = listOf(img1,img2,img3,img4)
        numpad = listOf(btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0)
        numpad.forEach { it.setOnClickListener(didTapNumpad())}

        btnSubmit.setOnClickListener(didTapSubmit())
        btnClear.setOnClickListener(didTapClear())
    }
    inner  class  didTapSubmit : View.OnClickListener{
        override fun onClick(p0: View?) {

        }
    }

   internal inner  class  didTapClear: View.OnClickListener{
        override fun onClick(p0: View?) {

         strCode =  strCode.substring(strCode.length-1)
            shouldUpdateCodeImage()

        }
    }

    inner class didTapNumpad : View.OnClickListener{
        override fun onClick(v: View?) {

            if (strCode.length <= 4){
                strCode += (v as Button).text
            }
            shouldUpdateCodeImage()
        }
    }
    fun shouldUpdateCodeImage(){
        for (x in 0 until codeImg.size){
            codeImg[x].background = if (strCode.length >= x+1) ContextCompat.getDrawable( applicationContext,R.drawable.ic_pin_cirlce_dot) else ContextCompat.getDrawable( applicationContext,R.drawable.ic_pin_circle)
        }
    }
}
package com.xpayworld.payment.ui.activation

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseActivitykt
import kotlinx.android.synthetic.main.activity_activation_code.*

class  ActiviationActivitykt : BaseActivitykt(){

    var edtextList = listOf<EditText>()
    var strCode  = ""
    override fun getLayout(): Int {
      return R.layout.activity_activation_code
        }

    override fun initView() {
       edtextList = listOf(edtext1,edtext2,edtext3,edtext4)
        edtextList.forEach { it.addTextChangedListener(onChangedEditText())}
    }

   internal  inner class onChangedEditText : TextWatcher{
        override fun onTextChanged(char : CharSequence?, p1: Int, p2: Int, count: Int) {
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            strCode = ""
            if (edtext1.isFocused && edtext1.text.length == 4){
                edtext2.requestFocus()
            }
            if (edtext2.isFocused && edtext2.text.length == 4){
                edtext3.requestFocus()
            }
            if (edtext3.isFocused && edtext3.text.length == 4){
                edtext4.requestFocus()
            }
            edtextList.forEach { action: EditText ->
                strCode += action.text.toString()
            }

            btnActivate.isEnabled = strCode.length == 16
        }
    }

}





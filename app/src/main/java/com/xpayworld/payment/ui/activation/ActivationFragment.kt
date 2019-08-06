package com.xpayworld.payment.ui.activation

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragmentkt
import com.xpayworld.payment.ui.transaction.DrawerLocker
import com.xpayworld.payment.util.CustomDialog
import com.xpayworld.payment.util.filter
import kotlinx.android.synthetic.main.fragment_activation_code.*


class ActivationFragment : BaseFragmentkt() {

    override fun getLayout(): Int {
        return R.layout.fragment_activation_code
    }

    var edtextList = listOf<EditText>()
    var strCode = ""
    lateinit var viewModel : ActivationViewModel

    override fun initView(view: View) {

        edtextList = listOf(edtext1, edtext2, edtext3, edtext4)
        edtextList.forEach { it.addTextChangedListener(onChangedEditText()) }

        viewModel = ViewModelProviders.of(activity!!).get(ActivationViewModel::class.java)



        viewModel.loadingVisibility.observe(this, Observer{
            isShow -> if (isShow == true) showProgress() else hideProgress()
        })



        viewModel.toolbarVisibility.observe(this, Observer{(activity as DrawerLocker).drawerEnabled(it)})


        viewModel.apiError.observe(this, Observer {
           if (it) {
               edtextList.forEach { editText -> editText.setBackgroundResource(R.drawable.tv_error)}
               errorGroup.visibility = View.VISIBLE
           }
           else{
               errorGroup.visibility = View.GONE
               edtextList.forEach { editText -> editText.setBackgroundResource(R.drawable.tv_activation)}
           }
        })
        viewModel.networkError.observe(this, Observer {
            CustomDialog(context!!).onError().show()
        })

        btnActivate.setOnClickListener(viewModel.activateClickListener)
    }

    internal inner class onChangedEditText : TextWatcher {
        override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, count: Int) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            strCode = ""
            if (edtext1.isFocused && edtext1.text.length == 4) {
                edtext2.requestFocus()
            }
            if (edtext2.isFocused && edtext2.text.length == 4) {
                edtext3.requestFocus()
            }
            if (edtext3.isFocused && edtext3.text.length == 4) {
                edtext4.requestFocus()
            }
            edtextList.forEach { action: EditText ->
                strCode += action.text.toString()
            }

            btnActivate.isEnabled = strCode.length == 16
        }
    }

}





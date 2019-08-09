package com.xpayworld.payment.ui.enterPin

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragmentkt
import com.xpayworld.payment.ui.transaction.ToolbarDelegate
import com.xpayworld.payment.util.SharedPrefStorage

import kotlinx.android.synthetic.main.fragment_enter_pin.*
import androidx.lifecycle.Observer
import com.xpayworld.payment.util.CustomDialog

class EnterPinFragment : BaseFragmentkt() {

    var numpad = listOf<Button>()
    var pinCodeImgArr = listOf<ImageView>()
    private lateinit var viewModel: EnterPinModelView

    override fun getLayout(): Int {
        return R.layout.fragment_enter_pin
    }

    override fun initView(view: View) {
        viewModel = ViewModelProviders.of(activity!!).get(EnterPinModelView::class.java)

        // Input
        pinCodeImgArr = listOf(img1, img2, img3, img4)
        numpad = listOf(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0)
        numpad.forEach { it.setOnClickListener(viewModel.numpadClickListener) }
        btnSubmit.setOnClickListener(viewModel.sumbitClickListener)
        btnClear.setOnClickListener(viewModel.clearClickListener)

        shouldCheckActivationKey()

        viewModel.pinCode
                .observe(this, Observer {
                    shouldUpdateCodeImage(it)
                })
        viewModel.toolbarVisibility.observe(this, Observer { (activity as ToolbarDelegate).showToolbar(it) })
        viewModel.loadingVisibility.observe(this, Observer {
            if (it) showProgress() else hideProgress()
        })

        viewModel.apiError.observe(this, Observer { it ->
            if (it) {
                val shake = AnimationUtils.loadAnimation(context!!, R.anim.pin_shake)
                     pinCodeImgArr.forEach {
                    it.startAnimation(shake)
                    }
          }
        })

        viewModel.networkError.observe(this, Observer {
            CustomDialog(context!!).onError(it).show()


        })

    }

    private fun shouldCheckActivationKey() {
        val sharedPref = context?.let { it -> SharedPrefStorage(it) }
        if (sharedPref!!.isEmpty("activationKey")) {
            findNavController().navigate(R.id.activationFragment)
        }
    }

    private fun shouldUpdateCodeImage(pinCode: String) {
        for (x in 0 until pinCodeImgArr.size) {
            val img = if (pinCode.length >= x + 1) R.drawable.ic_pin_cirlce_dot else R.drawable.ic_pin_circle
            pinCodeImgArr[x].setBackgroundResource(img)
        }
    }

}
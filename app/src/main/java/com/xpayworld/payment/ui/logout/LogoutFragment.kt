package com.xpayworld.payment.ui.logout

import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.xpayworld.payment.R
import com.xpayworld.payment.ui.base.kt.BaseFragment

class LogoutFragment  : BaseFragment(){
    override fun getLayout(): Any {
        return  R.layout.fragment_logout
    }

    override fun initView(view: View, container: ViewGroup?) {
        view.findNavController().navigate(R.id.enterPinFragment)
    }

}
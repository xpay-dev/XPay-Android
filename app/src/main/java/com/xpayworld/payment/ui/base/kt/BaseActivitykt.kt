package com.xpayworld.payment.ui.base.kt

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection

abstract  class BaseActivitykt : AppCompatActivity(), MvpViewkt {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        initView()
    }

    override fun showProgress() {

    }

    override fun showNetworkError() {

    }

    override fun hideProgress() {

    }


    abstract fun initView()

    private fun performDI() = AndroidInjection.inject(this)
}
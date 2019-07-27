package com.xpayworld.payment.ui.base.kt

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection

abstract  class BaseActivitykt : AppCompatActivity(), MvpViewkt {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
       // performDI()
        initView()

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    @LayoutRes abstract fun getLayout(): Int

    abstract fun initView()

    private fun performDI() = AndroidInjection.inject(this)
}
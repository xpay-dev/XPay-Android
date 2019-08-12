package com.xpayworld.payment.ui.base.kt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xpayworld.payment.util.CustomDialog
import dagger.android.AndroidInjection

abstract  class BaseActivity : AppCompatActivity(), MvpView ,BaseFragment.CallBack{
    private lateinit var dialog: CustomDialog
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        dialog = CustomDialog(applicationContext)
        initView()
    }

    override fun showProgress() {
        runOnUiThread {
            dialog.onLoading().show()}
    }

    override fun showNetworkError() {
        runOnUiThread {
            dialog.onError().show()
        }
    }

    override fun hideProgress() {
        runOnUiThread {
            dialog.onDismiss()}
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    abstract fun initView()

    private fun performDI() = AndroidInjection.inject(this)
}